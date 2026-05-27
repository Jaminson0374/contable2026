package co.posinvent.domain.repository;

import co.posinvent.domain.model.ProductionOrder;
import co.posinvent.domain.model.ProductionOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

public interface ProductionOrderRepository {
    ProductionOrder save(ProductionOrder order);
    Optional<ProductionOrder> findById(UUID id);
    Page<ProductionOrder> findAll(Pageable pageable);
    Page<ProductionOrder> findFiltered(ProductionOrderStatus status, UUID warehouseId,
                                        LocalDate from, LocalDate to, Pageable pageable);
    Optional<ProductionOrder> findByOrderNumber(String orderNumber);
}