package co.posinvent.domain.repository;

import co.posinvent.domain.model.ProductionBatch;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductionBatchRepository {
    ProductionBatch save(ProductionBatch batch);
    Optional<ProductionBatch> findById(UUID id);
    List<ProductionBatch> findByFormulaId(UUID formulaId);
}
