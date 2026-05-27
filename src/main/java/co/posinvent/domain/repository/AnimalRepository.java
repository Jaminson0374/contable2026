package co.posinvent.domain.repository;

import co.posinvent.domain.model.Animal;
import co.posinvent.domain.model.Animal.AnimalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface AnimalRepository {

    Animal save(Animal animal);

    Optional<Animal> findById(UUID id);

    Page<Animal> findAll(Pageable pageable);

    Page<Animal> findByStatus(AnimalStatus status, Pageable pageable);

    Page<Animal> searchByIcaLot(String icaLot, Pageable pageable);

    void deleteById(UUID id);
}
