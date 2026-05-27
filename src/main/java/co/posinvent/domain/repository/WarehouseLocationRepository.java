package co.posinvent.domain.repository;

import co.posinvent.domain.model.WarehouseLocation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseLocationRepository {
    List<WarehouseLocation> findByWarehouseId(UUID warehouseId);
    Optional<WarehouseLocation> findById(UUID id);
    WarehouseLocation save(WarehouseLocation entity);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
