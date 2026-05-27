package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductPresentationJpaRepository extends JpaRepository<ProductPresentationEntity, UUID> {

    List<ProductPresentationEntity> findByProductIdOrderByCode(UUID productId);

    Optional<ProductPresentationEntity> findByProductIdAndIsDefaultTrue(UUID productId);

    Optional<ProductPresentationEntity> findByProductIdAndCode(UUID productId, String code);

    void deleteByProductId(UUID productId);
}
