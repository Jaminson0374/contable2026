package co.posinvent.domain.repository;

import co.posinvent.domain.model.ProductModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductModelRepository {
    List<ProductModel> findAllActive();
    List<ProductModel> findByBrandId(UUID brandId);
    Optional<ProductModel> findById(UUID id);
    ProductModel save(ProductModel entity);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
