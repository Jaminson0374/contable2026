package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Animal.AnimalStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface AnimalJpaRepository extends JpaRepository<AnimalEntity, UUID> {

    Page<AnimalEntity> findByStatus(AnimalStatus status, Pageable pageable);

    Page<AnimalEntity> findBySupplierId(UUID supplierId, Pageable pageable);

    Page<AnimalEntity> findByIcaLotNumberContainingIgnoreCase(String icaLot, Pageable pageable);
}
