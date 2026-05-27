package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

interface ProductTypeJpaRepository extends JpaRepository<ProductTypeEntity, UUID> {
    List<ProductTypeEntity> findByActiveTrue();
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
