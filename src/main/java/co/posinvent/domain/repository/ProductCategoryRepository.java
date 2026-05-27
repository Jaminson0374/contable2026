package co.posinvent.domain.repository;

import co.posinvent.domain.model.ProductCategory;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductCategoryRepository {
    List<ProductCategory> findAllActive();
    Optional<ProductCategory> findById(UUID id);
    ProductCategory save(ProductCategory entity);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
