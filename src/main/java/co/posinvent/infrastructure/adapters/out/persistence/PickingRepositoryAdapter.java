package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Picking;
import co.posinvent.domain.repository.PickingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PickingRepositoryAdapter implements PickingRepository {

    private final PickingJpaRepository jpa;
    private final PickingMapper mapper;

    public PickingRepositoryAdapter(PickingJpaRepository jpa, PickingMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Picking save(Picking picking) {
        var entity = mapper.toEntity(picking);
        if (picking.items() != null) {
            var itemEntities = new java.util.ArrayList<PickingItemEntity>();
            for (var item : picking.items()) {
                var ie = mapper.toItemEntity(item);
                ie.setPicking(entity);
                itemEntities.add(ie);
            }
            entity.getItems().clear();
            entity.getItems().addAll(itemEntities);
        }
        return mapper.toDomain(jpa.save(entity));
    }

    @Override
    public Optional<Picking> findById(UUID id) {
        return jpa.findByIdWithItems(id).map(mapper::toDomain);
    }

    @Override
    public Page<Picking> findAll(Pageable pageable) {
        return jpa.findAll(pageable).map(mapper::toDomain);
    }

    @Override
    public Page<Picking> findByWarehouseId(UUID warehouseId, Pageable pageable) {
        return jpa.findByWarehouseId(warehouseId, pageable).map(mapper::toDomain);
    }
}
