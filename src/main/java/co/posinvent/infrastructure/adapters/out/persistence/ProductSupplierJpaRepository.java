package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

interface ProductSupplierJpaRepository extends JpaRepository<ProductSupplierEntity, UUID> {

    List<ProductSupplierEntity> findByProductId(UUID productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductSupplierEntity s WHERE s.product.id = :productId")
    void deleteByProductId(UUID productId);
}
