package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

interface WarehouseLocationJpaRepository extends JpaRepository<WarehouseLocationEntity, UUID> {
    List<WarehouseLocationEntity> findByWarehouseIdAndActiveTrue(UUID warehouseId);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
