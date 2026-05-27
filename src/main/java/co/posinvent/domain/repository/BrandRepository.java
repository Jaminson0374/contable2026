package co.posinvent.domain.repository;

import co.posinvent.domain.model.Brand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrandRepository {
    List<Brand> findAllActive();
    Optional<Brand> findById(UUID id);
    Brand save(Brand entity);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
