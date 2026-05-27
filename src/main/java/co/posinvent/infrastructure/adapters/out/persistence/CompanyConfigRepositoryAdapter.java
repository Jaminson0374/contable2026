package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.CompanyConfig;
import co.posinvent.domain.repository.CompanyConfigRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
class CompanyConfigRepositoryAdapter implements CompanyConfigRepository {

    private final CompanyConfigJpaRepository jpa;
    private final CompanyConfigMapper mapper;
    private final WarehouseJpaRepository warehouseJpa;

    CompanyConfigRepositoryAdapter(
            CompanyConfigJpaRepository jpa,
            CompanyConfigMapper mapper,
            WarehouseJpaRepository warehouseJpa
    ) {
        this.jpa = jpa;
        this.mapper = mapper;
        this.warehouseJpa = warehouseJpa;
    }

    @Override
    public Optional<CompanyConfig> findConfig() {
        return jpa.findById(1L).map(mapper::toDomain);
    }

    @Override
    public CompanyConfig save(CompanyConfig domain) {
        var entity = mapper.toEntity(domain);
        entity.setId(1L);

        var now = OffsetDateTime.now();
        var existing = jpa.findById(1L);
        if (existing.isPresent()) {
            entity.setCreatedAt(existing.get().getCreatedAt());
        } else {
            entity.setCreatedAt(now);
        }
        entity.setUpdatedAt(now);

        if (domain.mainWarehouseId() != null) {
            var warehouse = warehouseJpa.findById(domain.mainWarehouseId()).orElse(null);
            entity.setMainWarehouse(warehouse);
        } else {
            entity.setMainWarehouse(null);
        }

        var saved = jpa.save(entity);
        var reloaded = jpa.findById(saved.getId()).orElseThrow();
        return mapper.toDomain(reloaded);
    }
}
