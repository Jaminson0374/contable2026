package co.posinvent.application.usecase;

import co.posinvent.application.dto.BatchItemResponse;
import co.posinvent.application.dto.ProduceRequest;
import co.posinvent.application.dto.ProduceResponse;
import co.posinvent.domain.model.*;
import co.posinvent.domain.repository.*;
import co.posinvent.domain.service.BomExploder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FormulaProductionUseCase {

    private final ProductFormulaRepository formulaRepo;
    private final KardexRepository kardexRepo;
    private final RecordMovementUseCase recordMovementUseCase;
    private final ProductionBatchRepository batchRepo;
    private final CompanyConfigRepository configRepo;
    private final ProductRepository productRepo;
    private final BomExploder bomExploder;

    public FormulaProductionUseCase(
            ProductFormulaRepository formulaRepo,
            KardexRepository kardexRepo,
            RecordMovementUseCase recordMovementUseCase,
            ProductionBatchRepository batchRepo,
            CompanyConfigRepository configRepo,
            ProductRepository productRepo,
            BomExploder bomExploder) {
        this.formulaRepo = formulaRepo;
        this.kardexRepo = kardexRepo;
        this.recordMovementUseCase = recordMovementUseCase;
        this.batchRepo = batchRepo;
        this.configRepo = configRepo;
        this.productRepo = productRepo;
        this.bomExploder = bomExploder;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ProduceResponse produce(ProduceRequest request) {
        var parentProduct = productRepo.findById(request.formulaProductId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        // Step 1: Explode BOM recursively to get all leaf raw materials
        var explodedComponents = bomExploder.explode(request.formulaProductId(), request.quantity());
        if (explodedComponents.isEmpty()) {
            throw new IllegalArgumentException("El producto no tiene fórmula definida");
        }

        // Step 2: Merge duplicate raw materials (same product appears at multiple levels)
        var merged = new java.util.HashMap<UUID, BigDecimal>();
        for (var ec : explodedComponents) {
            merged.merge(ec.productId(), ec.totalQuantity(), BigDecimal::add);
        }

        // Step 3: Validate stock for each merged raw material
        var components = new ArrayList<ComponentCalc>();
        for (var entry : merged.entrySet()) {
            UUID productId = entry.getKey();
            BigDecimal requiredQty = entry.getValue();

            var component = productRepo.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Componente no encontrado: " + productId));

            BigDecimal currentStock = kardexRepo.getCurrentStock(productId, request.warehouseId());
            if (currentStock.compareTo(requiredQty) < 0) {
                throw new IllegalArgumentException(
                        "Stock insuficiente para " + component.name()
                                + ": requiere " + requiredQty + ", disponible " + currentStock);
            }
            components.add(new ComponentCalc(component, requiredQty, currentStock));
        }

        // Step 4: Calculate costs
        var config = configRepo.findConfig();
        String costingMethod = config.map(CompanyConfig::costingMethod)
                .filter(m -> m != null && !m.isBlank())
                .orElse("WEIGHTED_AVERAGE");

        String overheadAllocationBase = config.map(CompanyConfig::overheadAllocationBase)
                .filter(b -> b != null && !b.isBlank())
                .orElse("MOD");

        BigDecimal overheadRate = config.map(CompanyConfig::overheadRate)
                .orElse(BigDecimal.ZERO);

        BigDecimal mpdTotal = BigDecimal.ZERO;
        for (var comp : components) {
            BigDecimal unitCost = kardexRepo.getUnitCost(comp.component.id(), request.warehouseId(), costingMethod)
                    .orElse(BigDecimal.ZERO);
            comp.unitCost = unitCost;
            comp.totalCost = comp.requiredQty.multiply(unitCost);
            mpdTotal = mpdTotal.add(comp.totalCost);
        }

        BigDecimal laborCost = request.laborCost() != null ? request.laborCost() : BigDecimal.ZERO;
        BigDecimal overheadCost;
        if (request.overheadCost() != null) {
            overheadCost = request.overheadCost();
        } else {
            if ("MOD".equalsIgnoreCase(overheadAllocationBase)) {
                overheadCost = laborCost.multiply(overheadRate).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            } else {
                overheadCost = mpdTotal.multiply(overheadRate).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            }
        }

        BigDecimal totalCost = mpdTotal.add(laborCost).add(overheadCost);
        BigDecimal unitCost = totalCost.divide(request.quantity(), 4, RoundingMode.HALF_UP);

        // Step 5: Create ProductionBatch
        var batch = new ProductionBatch(
                null, request.formulaProductId(), request.quantity(), request.quantity(),
                mpdTotal, laborCost, overheadCost, totalCost, unitCost,
                BigDecimal.ZERO, BigDecimal.ZERO, request.notes(), null, null
        );
        var savedBatch = batchRepo.save(batch);

        // Step 6: Record kardex — consume raw materials
        var items = new ArrayList<BatchItemResponse>();
        for (var comp : components) {
            var movement = recordMovementUseCase.record(
                    comp.component.id(),
                    null,
                    request.warehouseId(),
                    MovementType.PRODUCTION_CONSUMPTION,
                    comp.requiredQty.negate(),
                    comp.unitCost,
                    comp.currentStock,
                    comp.currentStock.subtract(comp.requiredQty),
                    "PRODUCTION_BATCH",
                    savedBatch.id(),
                    "Consumo para producción del lote " + savedBatch.id()
            );

            items.add(new BatchItemResponse(
                    comp.component.id(),
                    comp.component.name(),
                    comp.requiredQty,
                    comp.requiredQty,
                    comp.unitCost,
                    comp.totalCost,
                    movement.id()
            ));
        }

        // Step 7: Record kardex output for finished product
        recordMovementUseCase.record(
                request.formulaProductId(),
                null,
                request.warehouseId(),
                MovementType.PRODUCTION_OUTPUT,
                request.quantity(),
                unitCost,
                kardexRepo.getCurrentStock(request.formulaProductId(), request.warehouseId()),
                kardexRepo.getCurrentStock(request.formulaProductId(), request.warehouseId()).add(request.quantity()),
                "PRODUCTION_BATCH",
                savedBatch.id(),
                "Producción del lote " + savedBatch.id()
        );

        // Step 8: Recalculate stock
        productRepo.recalculateTotalStock(request.formulaProductId());
        for (var comp : components) {
            productRepo.recalculateTotalStock(comp.component.id());
        }

        return new ProduceResponse(
                savedBatch.id(),
                parentProduct.name(),
                request.quantity(),
                mpdTotal,
                laborCost,
                overheadCost,
                totalCost,
                unitCost,
                BigDecimal.ZERO,
                items
        );
    }

    private static class ComponentCalc {
        final Product component;
        final BigDecimal requiredQty;
        final BigDecimal currentStock;
        BigDecimal unitCost = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;

        ComponentCalc(Product component, BigDecimal requiredQty, BigDecimal currentStock) {
            this.component = component;
            this.requiredQty = requiredQty;
            this.currentStock = currentStock;
        }
    }
}
