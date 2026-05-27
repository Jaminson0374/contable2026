package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

interface ProductGroupJpaRepository extends JpaRepository<ProductGroupEntity, UUID> {
    List<ProductGroupEntity> findByActiveTrueOrderByNameAsc();
    List<ProductGroupEntity> findByCategoryIdAndActiveTrue(UUID categoryId);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
