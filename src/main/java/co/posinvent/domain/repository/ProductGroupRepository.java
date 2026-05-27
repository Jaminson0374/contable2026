package co.posinvent.domain.repository;

import co.posinvent.domain.model.ProductGroup;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductGroupRepository {
    List<ProductGroup> findAllActive();
    List<ProductGroup> findByCategoryId(UUID categoryId);
    Optional<ProductGroup> findById(UUID id);
    ProductGroup save(ProductGroup entity);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
