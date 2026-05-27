package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Batch.BatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

interface BatchJpaRepository extends JpaRepository<BatchEntity, UUID> {

    Page<BatchEntity> findByStatus(BatchStatus status, Pageable pageable);

    Page<BatchEntity> findByWarehouseId(UUID warehouseId, Pageable pageable);

    List<BatchEntity> findBySourceReceiptId(UUID sourceReceiptId);
}
