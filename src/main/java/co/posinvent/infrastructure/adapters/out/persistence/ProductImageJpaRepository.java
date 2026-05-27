package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

interface ProductImageJpaRepository extends JpaRepository<ProductImageEntity, UUID> {

    List<ProductImageEntity> findByProductId(UUID productId);

    @Query("SELECT i FROM ProductImageEntity i WHERE i.product.id IN :productIds")
    List<ProductImageEntity> findByProductIds(List<UUID> productIds);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductImageEntity i WHERE i.product.id = :productId")
    void deleteByProductId(UUID productId);
}
