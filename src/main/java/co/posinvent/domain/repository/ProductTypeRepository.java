package co.posinvent.domain.repository;

import co.posinvent.domain.model.ProductType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductTypeRepository {
    List<ProductType> findAllActive();
    Optional<ProductType> findById(UUID id);
    ProductType save(ProductType entity);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
