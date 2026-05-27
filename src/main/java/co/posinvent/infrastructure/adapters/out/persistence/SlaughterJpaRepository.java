package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface SlaughterJpaRepository extends JpaRepository<SlaughterEntity, UUID> {

    Optional<SlaughterEntity> findByAnimalId(UUID animalId);
}
