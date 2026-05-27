package co.posinvent.application.usecase;

import co.posinvent.application.dto.ManualDesposteRequest;
import co.posinvent.application.dto.ManualDesposteResponse;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.exception.ResourceNotFoundException;
import co.posinvent.domain.model.Batch;
import co.posinvent.domain.model.InventoryStock;
import co.posinvent.domain.model.ManualDespostePlan;
import co.posinvent.domain.model.MovementType;
import co.posinvent.domain.repository.BatchRepository;
import co.posinvent.domain.repository.ProductRepository;
import co.posinvent.domain.repository.StockRepository;
import co.posinvent.domain.repository.WarehouseRepository;
import co.posinvent.domain.service.ManualDesposteDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.UUID;

@Service
public class ManualDesposteUseCase {

    private final BatchRepository batchRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockRepository stockRepository;
    private final ManualDesposteDomainService domainService;
    private final RecordMovementUseCase recordMovement;

    public ManualDesposteUseCase(
            BatchRepository batchRepository,
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository,
            StockRepository stockRepository,
            ManualDesposteDomainService domainService,
            RecordMovementUseCase recordMovement
    ) {
        this.batchRepository = batchRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.stockRepository = stockRepository;
        this.domainService = domainService;
        this.recordMovement = recordMovement;
    }

    @Transactional
    public ManualDesposteResponse processManual(ManualDesposteRequest request) {
        var batch = batchRepository.findById(request.sourceBatchId())
                .orElseThrow(() -> new ResourceNotFoundException("Lote", request.sourceBatchId()));

        validateCutReferences(request);

        var plan = domainService.planForExistingBatch(batch, toDomainCommand(request));

        for (var stockUpsert : plan.stockUpserts()) {
            upsertStock(stockUpsert);
        }

        closeBatch(batch);
        return ManualDesposteResponse.from(plan);
    }

    private void validateCutReferences(ManualDesposteRequest request) {
        var productIds = new LinkedHashSet<UUID>();
        var warehouseIds = new LinkedHashSet<UUID>();

        for (var cut : request.cuts()) {
            productIds.add(cut.productId());
            warehouseIds.add(cut.warehouseId());
        }

        for (var productId : productIds) {
            var product = productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Producto", productId));

            if (!product.active()) {
                throw new BusinessException(
                        "INACTIVE_DESPOSTE_PRODUCT",
                        "El producto resultante esta inactivo: " + productId
                );
            }

            if (!product.inventoriable()) {
                throw new BusinessException(
                        "NON_INVENTORIABLE_DESPOSTE_PRODUCT",
                        "El producto resultante debe ser inventariable: " + productId
                );
            }
        }

        for (var warehouseId : warehouseIds) {
            var warehouse = warehouseRepository.findById(warehouseId)
                    .orElseThrow(() -> new ResourceNotFoundException("Bodega", warehouseId));

            if (!warehouse.active()) {
                throw new BusinessException(
                        "INACTIVE_DESPOSTE_WAREHOUSE",
                        "La bodega destino esta inactiva: " + warehouseId
                );
            }
        }
    }

    private ManualDespostePlan.Command toDomainCommand(ManualDesposteRequest request) {
        return new ManualDespostePlan.Command(
                request.sourceBatchId(),
                request.sourceType(),
                request.manualJustification(),
                request.wasteWeight(),
                request.shrinkWeight(),
                request.notes(),
                request.cuts().stream()
                        .map(cut -> new ManualDespostePlan.ManualDesposteCutCommand(
                                cut.productId(),
                                cut.warehouseId(),
                                cut.weight(),
                                cut.suggestedSalePrice()
                        ))
                        .toList()
        );
    }

    private void upsertStock(ManualDespostePlan.StockUpsertDraft stockUpsert) {
        var existing = stockRepository.findByProductBatchWarehouse(
                stockUpsert.productId(),
                stockUpsert.batchId(),
                stockUpsert.warehouseId());

        if (existing.isPresent()) {
            var stock = existing.get();
            var updatedQuantity = stock.currentQuantity().add(stockUpsert.quantityDelta());
            var updatedUnitCost = resolveUnitCost(stock, stockUpsert, updatedQuantity);

            stockRepository.save(new InventoryStock(
                    stock.id(),
                    stock.productId(),
                    stock.batchId(),
                    stock.warehouseId(),
                    updatedQuantity,
                    stock.committedQuantity(),
                    updatedUnitCost,
                    stock.createdAt(),
                    stock.updatedAt()
            ));
            productRepository.recalculateTotalStock(stockUpsert.productId());
            recordMovement.record(
                    stockUpsert.productId(), stockUpsert.batchId(), stockUpsert.warehouseId(),
                    MovementType.ENTRY,
                    stockUpsert.quantityDelta(), stockUpsert.unitCost(),
                    stock.currentQuantity(), updatedQuantity,
                    "DESPOSTE", stockUpsert.batchId(),
                    "Desposte — corte de producto"
            );
            return;
        }

        stockRepository.save(new InventoryStock(
                null,
                stockUpsert.productId(),
                stockUpsert.batchId(),
                stockUpsert.warehouseId(),
                stockUpsert.quantityDelta(),
                BigDecimal.ZERO,
                stockUpsert.unitCost(),
                null,
                null
        ));
        productRepository.recalculateTotalStock(stockUpsert.productId());
        recordMovement.record(
                stockUpsert.productId(), stockUpsert.batchId(), stockUpsert.warehouseId(),
                MovementType.ENTRY,
                stockUpsert.quantityDelta(), stockUpsert.unitCost(),
                BigDecimal.ZERO, stockUpsert.quantityDelta(),
                "DESPOSTE", stockUpsert.batchId(),
                "Desposte — nuevo producto"
        );
    }

    private BigDecimal resolveUnitCost(
            InventoryStock existing,
            ManualDespostePlan.StockUpsertDraft stockUpsert,
            BigDecimal updatedQuantity
    ) {
        var existingAllocated = existing.currentQuantity().multiply(existing.unitCost());
        var newAllocated = stockUpsert.quantityDelta().multiply(stockUpsert.unitCost());
        return existingAllocated.add(newAllocated)
                .divide(updatedQuantity, 6, java.math.RoundingMode.HALF_UP);
    }

    private void closeBatch(Batch batch) {
        batchRepository.save(new Batch(
                batch.id(),
                batch.supplierId(),
                batch.warehouseId(),
                batch.entryDate(),
                batch.initialWeight(),
                batch.purchaseCost(),
                Batch.BatchStatus.CLOSED,
                batch.notes(),
                null,              // expirationDate
                batch.createdBy(),
                batch.createdAt(),
                batch.updatedAt(),
                batch.sourceReceiptId(),
                batch.ocId()
        ));
    }
}
