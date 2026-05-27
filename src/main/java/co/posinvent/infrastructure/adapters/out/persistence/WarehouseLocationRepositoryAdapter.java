package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.WarehouseLocation;
import co.posinvent.domain.repository.WarehouseLocationRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class WarehouseLocationRepositoryAdapter implements WarehouseLocationRepository {

    private final WarehouseLocationJpaRepository jpa;
    private final WarehouseLocationMapper mapper;

    WarehouseLocationRepositoryAdapter(WarehouseLocationJpaRepository jpa, WarehouseLocationMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public List<WarehouseLocation> findByWarehouseId(UUID warehouseId) {
        return jpa.findByWarehouseIdAndActiveTrue(warehouseId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<WarehouseLocation> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public WarehouseLocation save(WarehouseLocation domain) {
        return mapper.toDomain(jpa.save(mapper.toEntity(domain)));
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, UUID id) {
        return jpa.existsByNameAndIdNot(name, id);
    }
}
