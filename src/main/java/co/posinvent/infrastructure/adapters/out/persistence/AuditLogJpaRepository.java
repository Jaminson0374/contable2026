package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface AuditLogJpaRepository extends JpaRepository<AuditLogEntity, UUID> {

    @Query("""
        SELECT a FROM AuditLogEntity a
        WHERE (:entityType IS NULL OR a.entityType = :entityType)
          AND (:userId IS NULL OR a.userId = :userId)
          AND (:action IS NULL OR a.action = :action)
          AND (:from IS NULL OR a.createdAt >= :from)
          AND (:to   IS NULL OR a.createdAt <= :to)
        ORDER BY a.createdAt DESC
    """)
    Page<AuditLogEntity> findFiltered(
        @Param("entityType") String entityType,
        @Param("userId") UUID userId,
        @Param("action") String action,
        @Param("from") OffsetDateTime from,
        @Param("to") OffsetDateTime to,
        Pageable pageable
    );
}
