package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

interface ProductCategoryJpaRepository extends JpaRepository<ProductCategoryEntity, UUID> {
    List<ProductCategoryEntity> findByActiveTrue();
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
