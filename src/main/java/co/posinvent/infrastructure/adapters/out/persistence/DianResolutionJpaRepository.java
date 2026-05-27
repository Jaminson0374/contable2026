package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface DianResolutionJpaRepository extends JpaRepository<DianResolutionEntity, UUID> {

    Optional<DianResolutionEntity> findByActiveTrue();

    @Modifying
    @Query("UPDATE DianResolutionEntity r SET r.active = false WHERE r.active = true AND r.id != :id")
    void deactivateAllExcept(@Param("id") UUID id);

    @Modifying
    @Query("UPDATE DianResolutionEntity r SET r.active = false")
    void deactivateAll();
}
