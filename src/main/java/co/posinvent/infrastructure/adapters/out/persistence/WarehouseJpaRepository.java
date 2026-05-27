package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Warehouse.WarehouseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface WarehouseJpaRepository extends JpaRepository<WarehouseEntity, UUID> {

    List<WarehouseEntity> findByActiveTrueOrderByName();

    Optional<WarehouseEntity> findFirstByActiveTrueAndWarehouseType(WarehouseType type);

    List<WarehouseEntity> findByNameContainingIgnoreCaseOrderByName(String name);
}
