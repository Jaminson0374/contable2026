package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

interface ProductModelJpaRepository extends JpaRepository<ProductModelEntity, UUID> {
    List<ProductModelEntity> findByActiveTrueOrderByNameAsc();
    List<ProductModelEntity> findByBrandIdAndActiveTrue(UUID brandId);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
    boolean existsByNameAndBrandId(String name, UUID brandId);
}
