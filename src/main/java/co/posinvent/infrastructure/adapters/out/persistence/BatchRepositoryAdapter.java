package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Batch;
import co.posinvent.domain.model.Batch.BatchStatus;
import co.posinvent.domain.repository.BatchRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class BatchRepositoryAdapter implements BatchRepository {

    private final BatchJpaRepository jpa;
    private final BatchMapper mapper;

    BatchRepositoryAdapter(BatchJpaRepository jpa, BatchMapper mapper) {
        this.jpa    = jpa;
        this.mapper = mapper;
    }

    @Override public Batch save(Batch batch) {
        return mapper.toDomain(jpa.save(mapper.toEntity(batch)));
    }

    @Override public Optional<Batch> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override public Page<Batch> findAll(Pageable pageable) {
        return jpa.findAll(pageable).map(mapper::toDomain);
    }

    @Override public Page<Batch> findByStatus(BatchStatus status, Pageable pageable) {
        return jpa.findByStatus(status, pageable).map(mapper::toDomain);
    }

    @Override public Page<Batch> findByWarehouse(UUID warehouseId, Pageable pageable) {
        return jpa.findByWarehouseId(warehouseId, pageable).map(mapper::toDomain);
    }

    @Override public List<Batch> findBySourceReceiptId(UUID sourceReceiptId) {
        return jpa.findBySourceReceiptId(sourceReceiptId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
