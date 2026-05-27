package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

interface ShipmentJpaRepository extends JpaRepository<ShipmentEntity, UUID> {

    @Query("SELECT s FROM ShipmentEntity s LEFT JOIN FETCH s.items WHERE s.id = :id")
    Optional<ShipmentEntity> findByIdWithItems(@Param("id") UUID id);

    Page<ShipmentEntity> findByTransportGuideId(UUID transportGuideId, Pageable pageable);
}
