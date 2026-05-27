package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface AuditLogJpaRepository extends JpaRepository<AuditLogEntity, UUID> {

    Page<AuditLogEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);

    Page<AuditLogEntity> findByEntityTypeOrderByCreatedAtDesc(String entityType, Pageable pageable);

    Page<AuditLogEntity> findByCreatedAtBetweenOrderByCreatedAtDesc(
            OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    Page<AuditLogEntity> findByEntityTypeAndCreatedAtBetweenOrderByCreatedAtDesc(
            String entityType, OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    Page<AuditLogEntity> findByEntityTypeAndUserIdAndCreatedAtBetweenOrderByCreatedAtDesc(
            String entityType, UUID userId, OffsetDateTime from, OffsetDateTime to, Pageable pageable);

    Page<AuditLogEntity> findByEntityTypeAndUserIdAndActionAndCreatedAtBetweenOrderByCreatedAtDesc(
            String entityType, UUID userId, String action,
            OffsetDateTime from, OffsetDateTime to, Pageable pageable);
}
