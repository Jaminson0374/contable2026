package co.posinvent.application.usecase;

import co.posinvent.application.dto.ProductionOrderRequest;
import co.posinvent.application.dto.ProductionOrderResponse;
import co.posinvent.domain.model.ProductionOrder;
import co.posinvent.domain.model.ProductionOrderStatus;
import co.posinvent.domain.repository.ProductionOrderRepository;
import co.posinvent.domain.repository.ProductRepository;
import co.posinvent.domain.repository.WarehouseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class ManageProductionOrderUseCase {

    private final ProductionOrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final WarehouseRepository warehouseRepo;

    public ManageProductionOrderUseCase(
            ProductionOrderRepository orderRepo,
            ProductRepository productRepo,
            WarehouseRepository warehouseRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.warehouseRepo = warehouseRepo;
    }

    @Transactional
    public ProductionOrderResponse create(ProductionOrderRequest request) {
        productRepo.findById(request.formulaId())
                .orElseThrow(() -> new IllegalArgumentException("Producto fórmula no encontrado"));
        warehouseRepo.findById(request.warehouseId())
                .orElseThrow(() -> new IllegalArgumentException("Bodega no encontrada"));

        String datePart = request.plannedDate().toString().replace("-", "");
        int seq = (int) (System.currentTimeMillis() % 100000);
        String orderNumber = "PO-" + datePart + "-" + String.format("%05d", seq);

        var order = new ProductionOrder(
                null, orderNumber, request.formulaId(), request.plannedQuantity(),
                request.plannedDate(), ProductionOrderStatus.PLANNED,
                request.warehouseId(), request.machineryId(), request.notes(),
                "SYSTEM", null, null, null
        );
        return ProductionOrderResponse.from(orderRepo.save(order));
    }

    @Transactional
    public ProductionOrderResponse approve(UUID id) {
        var order = orderRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));
        if (order.status() != ProductionOrderStatus.PLANNED) {
            throw new IllegalStateException("Solo órdenes en PLANNED pueden ser aprobadas");
        }
        var approved = new ProductionOrder(
                order.id(), order.orderNumber(), order.formulaId(), order.plannedQuantity(),
                order.plannedDate(), ProductionOrderStatus.APPROVED,
                order.warehouseId(), order.machineryId(), order.notes(),
                order.createdBy(), "SYSTEM", order.createdAt(), OffsetDateTime.now()
        );
        return ProductionOrderResponse.from(orderRepo.save(approved));
    }

    @Transactional
    public ProductionOrderResponse cancel(UUID id) {
        var order = orderRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));
        if (order.status() == ProductionOrderStatus.COMPLETED) {
            throw new IllegalStateException("Órdenes COMPLETED no pueden cancelarse");
        }
        var cancelled = new ProductionOrder(
                order.id(), order.orderNumber(), order.formulaId(), order.plannedQuantity(),
                order.plannedDate(), ProductionOrderStatus.CANCELLED,
                order.warehouseId(), order.machineryId(), order.notes(),
                order.createdBy(), order.approvedBy(), order.createdAt(), order.approvedAt()
        );
        return ProductionOrderResponse.from(orderRepo.save(cancelled));
    }

    @Transactional(readOnly = true)
    public ProductionOrderResponse getById(UUID id) {
        return orderRepo.findById(id)
                .map(ProductionOrderResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada"));
    }

    @Transactional(readOnly = true)
    public Page<ProductionOrderResponse> list(ProductionOrderStatus status, UUID warehouseId,
                                               LocalDate from, LocalDate to, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductionOrder> orders;
        if (status != null || warehouseId != null || from != null || to != null) {
            orders = orderRepo.findFiltered(status, warehouseId, from, to, pageable);
        } else {
            orders = orderRepo.findAll(pageable);
        }
        return orders.map(ProductionOrderResponse::from);
    }
}
