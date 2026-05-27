package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.IdentificationType;
import co.posinvent.domain.repository.IdentificationTypeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class IdentificationTypeRepositoryAdapter implements IdentificationTypeRepository {

    private final IdentificationTypeJpaRepository jpa;
    private final IdentificationTypeMapper mapper;

    IdentificationTypeRepositoryAdapter(IdentificationTypeJpaRepository jpa, IdentificationTypeMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public List<IdentificationType> findAllActive() {
        return jpa.findByActiveTrue().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<IdentificationType> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public IdentificationType save(IdentificationType type) {
        IdentificationTypeEntity entity = mapper.toEntity(type);
        entity.setActive(true);
        return mapper.toDomain(jpa.save(entity));
    }
}
