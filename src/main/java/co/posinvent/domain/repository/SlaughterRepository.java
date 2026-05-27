package co.posinvent.domain.repository;

import co.posinvent.domain.model.Slaughter;

import java.util.Optional;
import java.util.UUID;

public interface SlaughterRepository {

    Slaughter save(Slaughter slaughter);

    Optional<Slaughter> findById(UUID id);

    Optional<Slaughter> findByAnimalId(UUID animalId);
}
