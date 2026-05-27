package co.posinvent.domain.repository;

import co.posinvent.domain.model.ProductState;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductStateRepository {
    List<ProductState> findAllActive();
    Optional<ProductState> findById(UUID id);
    ProductState save(ProductState entity);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
