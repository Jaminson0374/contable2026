package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Warehouse;
import co.posinvent.domain.model.Warehouse.WarehouseType;
import co.posinvent.domain.repository.WarehouseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class WarehouseRepositoryAdapter implements WarehouseRepository {

    private final WarehouseJpaRepository jpa;
    private final WarehouseMapper mapper;

    WarehouseRepositoryAdapter(WarehouseJpaRepository jpa, WarehouseMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public List<Warehouse> findAllActive() {
        return jpa.findByActiveTrueOrderByName().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Warehouse> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Warehouse> findFirstActiveByType(WarehouseType type) {
        return jpa.findFirstByActiveTrueAndWarehouseType(type)
                .map(mapper::toDomain);
    }

    @Override
    public List<Warehouse> findByNameContaining(String query) {
        return jpa.findByNameContainingIgnoreCaseOrderByName(query).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
