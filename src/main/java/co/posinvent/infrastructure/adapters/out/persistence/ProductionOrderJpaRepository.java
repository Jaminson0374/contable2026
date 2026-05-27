package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductionOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

interface ProductionOrderJpaRepository extends JpaRepository<ProductionOrderEntity, UUID> {

    Optional<ProductionOrderEntity> findByOrderNumber(String orderNumber);

    @Query("""
        SELECT p FROM ProductionOrderEntity p WHERE
        (:status IS NULL OR p.status = :status)
        AND (:warehouseId IS NULL OR p.warehouseId = :warehouseId)
        AND (:from IS NULL OR p.plannedDate >= :from)
        AND (:to IS NULL OR p.plannedDate <= :to)
        ORDER BY p.createdAt DESC
    """)
    Page<ProductionOrderEntity> findFiltered(
            @Param("status") ProductionOrderStatus status,
            @Param("warehouseId") UUID warehouseId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable);
}
