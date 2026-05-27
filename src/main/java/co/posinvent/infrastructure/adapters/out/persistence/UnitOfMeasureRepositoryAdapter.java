package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.UnitOfMeasure;
import co.posinvent.domain.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class UnitOfMeasureRepositoryAdapter implements UnitOfMeasureRepository {

    private final UnitOfMeasureJpaRepository jpa;
    private final UnitOfMeasureMapper mapper;

    UnitOfMeasureRepositoryAdapter(UnitOfMeasureJpaRepository jpa, UnitOfMeasureMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public List<UnitOfMeasure> findAllActive() {
        return jpa.findByActiveTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<UnitOfMeasure> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public UnitOfMeasure save(UnitOfMeasure domain) {
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
