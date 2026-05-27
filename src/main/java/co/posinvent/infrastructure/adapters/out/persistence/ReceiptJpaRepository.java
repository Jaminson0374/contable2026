package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

interface ReceiptJpaRepository extends JpaRepository<ReceiptEntity, UUID> {

    @Query("SELECT r FROM ReceiptEntity r LEFT JOIN FETCH r.items WHERE r.id = :id")
    Optional<ReceiptEntity> findByIdWithItems(@Param("id") UUID id);

    Page<ReceiptEntity> findByWarehouseId(UUID warehouseId, Pageable pageable);

    Page<ReceiptEntity> findBySupplierId(UUID supplierId, Pageable pageable);
}
