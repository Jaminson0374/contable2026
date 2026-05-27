package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

interface ProductWarehouseJpaRepository extends JpaRepository<ProductWarehouseEntity, UUID> {

    List<ProductWarehouseEntity> findByProductId(UUID productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductWarehouseEntity w WHERE w.product.id = :productId")
    void deleteByProductId(UUID productId);
}
