package co.posinvent.domain.repository;

import co.posinvent.domain.model.Picking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PickingRepository {

    Picking save(Picking picking);

    Optional<Picking> findById(UUID id);

    Page<Picking> findAll(Pageable pageable);

    Page<Picking> findByWarehouseId(UUID warehouseId, Pageable pageable);
}
