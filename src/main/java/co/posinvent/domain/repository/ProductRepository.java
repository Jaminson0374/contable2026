package co.posinvent.domain.repository;

import co.posinvent.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(UUID id);

    Page<Product> findAll(Pageable pageable);

    Page<Product> searchByName(String name, Pageable pageable);

    Page<Product> searchByBarcode(String barcode, Pageable pageable);

    Optional<Product> findByProductCode(String productCode);

    boolean existsByProductCode(String productCode);

    boolean existsByProductCodeAndIdNot(String productCode, UUID id);

    void recalculateTotalStock(UUID productId);
}
