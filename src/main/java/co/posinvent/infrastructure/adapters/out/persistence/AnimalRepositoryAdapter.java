package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Animal;
import co.posinvent.domain.model.Animal.AnimalStatus;
import co.posinvent.domain.repository.AnimalRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
class AnimalRepositoryAdapter implements AnimalRepository {

    private final AnimalJpaRepository jpa;
    private final AnimalMapper mapper;

    AnimalRepositoryAdapter(AnimalJpaRepository jpa, AnimalMapper mapper) {
        this.jpa    = jpa;
        this.mapper = mapper;
    }

    @Override public Animal save(Animal animal) {
        return mapper.toDomain(jpa.save(mapper.toEntity(animal)));
    }

    @Override public Optional<Animal> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override public Page<Animal> findAll(Pageable pageable) {
        return jpa.findAll(pageable).map(mapper::toDomain);
    }

    @Override public Page<Animal> findByStatus(AnimalStatus status, Pageable pageable) {
        return jpa.findByStatus(status, pageable).map(mapper::toDomain);
    }

    @Override public Page<Animal> searchByIcaLot(String icaLot, Pageable pageable) {
        return jpa.findByIcaLotNumberContainingIgnoreCase(icaLot, pageable).map(mapper::toDomain);
    }

    @Override public void deleteById(UUID id) {
        jpa.deleteById(id);
    }
}
