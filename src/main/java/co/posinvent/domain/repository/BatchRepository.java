package co.posinvent.domain.repository;

import co.posinvent.domain.model.Batch;
import co.posinvent.domain.model.Batch.BatchStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BatchRepository {

    Batch save(Batch batch);

    Optional<Batch> findById(UUID id);

    Page<Batch> findAll(Pageable pageable);

    Page<Batch> findByStatus(BatchStatus status, Pageable pageable);

    Page<Batch> findByWarehouse(UUID warehouseId, Pageable pageable);

    List<Batch> findBySourceReceiptId(UUID sourceReceiptId);
}
