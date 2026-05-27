package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {

    Page<ProductEntity> findAllByOrderByNameAsc(Pageable pageable);

    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<ProductEntity> findByBarcodeContaining(String barcode, Pageable pageable);

    Optional<ProductEntity> findByProductCode(String productCode);

    boolean existsByProductCode(String productCode);

    boolean existsByProductCodeAndIdNot(String productCode, UUID id);

    @Query("SELECT COUNT(p) FROM ProductEntity p WHERE p.incomeAccount.id = :pucId"
        + " OR p.inventoryAccount.id = :pucId OR p.costOfSalesAcct.id = :pucId")
    long countByPucAccountId(@Param("pucId") UUID pucId);

    @Modifying
    @Query(value = """
        UPDATE products p SET total_stock = COALESCE(
            (SELECT SUM(v.total_quantity) FROM v_available_stock v WHERE v.product_id = p.id), 0
        )
        WHERE p.id = :productId
        """, nativeQuery = true)
    int recalculateTotalStock(@Param("productId") UUID productId);

    @Modifying
    @Query(value = """
        UPDATE products p SET total_stock = COALESCE(
            (SELECT SUM(v.total_quantity) FROM v_available_stock v WHERE v.product_id = p.id), 0
        )
        """, nativeQuery = true)
    int recalculateAllTotalStock();
}
