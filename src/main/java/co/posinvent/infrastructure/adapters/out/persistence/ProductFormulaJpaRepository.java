package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductFormulaJpaRepository extends JpaRepository<ProductFormulaEntity, UUID> {

    List<ProductFormulaEntity> findByParentProductIdOrderBySequenceNumber(UUID parentProductId);

    List<ProductFormulaEntity> findAllByComponentProductId(UUID componentProductId);

    void deleteByParentProductId(UUID parentProductId);
}
