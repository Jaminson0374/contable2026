package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

interface PickingJpaRepository extends JpaRepository<PickingEntity, UUID> {

    @Query("SELECT p FROM PickingEntity p LEFT JOIN FETCH p.items WHERE p.id = :id")
    Optional<PickingEntity> findByIdWithItems(@Param("id") UUID id);

    Page<PickingEntity> findByWarehouseId(UUID warehouseId, Pageable pageable);
}
