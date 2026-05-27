package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductionBatchJpaRepository extends JpaRepository<ProductionBatchEntity, UUID> {

    List<ProductionBatchEntity> findByFormulaIdOrderByCreatedAtDesc(UUID formulaId);
}
