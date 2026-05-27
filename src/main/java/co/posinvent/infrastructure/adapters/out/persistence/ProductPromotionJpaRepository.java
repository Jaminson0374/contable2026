package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

interface ProductPromotionJpaRepository extends JpaRepository<ProductPromotionEntity, UUID> {

    List<ProductPromotionEntity> findByProductId(UUID productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductPromotionEntity p WHERE p.product.id = :productId")
    void deleteByProductId(UUID productId);
}
