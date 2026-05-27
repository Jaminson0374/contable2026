package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Slaughter;
import co.posinvent.domain.repository.SlaughterRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
class SlaughterRepositoryAdapter implements SlaughterRepository {

    private final SlaughterJpaRepository jpa;
    private final SlaughterMapper mapper;

    SlaughterRepositoryAdapter(SlaughterJpaRepository jpa, SlaughterMapper mapper) {
        this.jpa    = jpa;
        this.mapper = mapper;
    }

    @Override public Slaughter save(Slaughter slaughter) {
        return mapper.toDomain(jpa.save(mapper.toEntity(slaughter)));
    }

    @Override public Optional<Slaughter> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override public Optional<Slaughter> findByAnimalId(UUID animalId) {
        return jpa.findByAnimalId(animalId).map(mapper::toDomain);
    }
}
