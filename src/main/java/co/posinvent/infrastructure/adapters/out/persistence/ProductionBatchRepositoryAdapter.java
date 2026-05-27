package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductionBatch;
import co.posinvent.domain.repository.ProductionBatchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class ProductionBatchRepositoryAdapter implements ProductionBatchRepository {

    private final ProductionBatchJpaRepository jpa;
    private final ProductionBatchMapper mapper;

    ProductionBatchRepositoryAdapter(ProductionBatchJpaRepository jpa, ProductionBatchMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public ProductionBatch save(ProductionBatch batch) {
        return mapper.toDomain(jpa.save(mapper.toEntity(batch)));
    }

    @Override
    public Optional<ProductionBatch> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ProductionBatch> findByFormulaId(UUID formulaId) {
        return mapper.toDomainList(jpa.findByFormulaIdOrderByCreatedAtDesc(formulaId));
    }
}
