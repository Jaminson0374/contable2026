package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface AuditLogJpaRepository extends JpaRepository<AuditLogEntity, UUID> {

    @Query(value = """
        SELECT * FROM audit_log a
        WHERE (CAST(:entityType AS text) IS NULL OR a.entity_type = CAST(:entityType AS text))
          AND (CAST(:userId AS uuid) IS NULL OR a.user_id = CAST(:userId AS uuid))
          AND (CAST(:action AS text) IS NULL OR a.action = CAST(:action AS text))
          AND (CAST(:from AS timestamptz) IS NULL OR a.created_at >= CAST(:from AS timestamptz))
          AND (CAST(:to AS timestamptz) IS NULL OR a.created_at <= CAST(:to AS timestamptz))
        ORDER BY a.created_at DESC
        """,
        countQuery = """
        SELECT COUNT(*) FROM audit_log a
        WHERE (CAST(:entityType AS text) IS NULL OR a.entity_type = CAST(:entityType AS text))
          AND (CAST(:userId AS uuid) IS NULL OR a.user_id = CAST(:userId AS uuid))
          AND (CAST(:action AS text) IS NULL OR a.action = CAST(:action AS text))
          AND (CAST(:from AS timestamptz) IS NULL OR a.created_at >= CAST(:from AS timestamptz))
          AND (CAST(:to AS timestamptz) IS NULL OR a.created_at <= CAST(:to AS timestamptz))
        """,
        nativeQuery = true)
    Page<AuditLogEntity> findFiltered(
        @Param("entityType") String entityType,
        @Param("userId") UUID userId,
        @Param("action") String action,
        @Param("from") OffsetDateTime from,
        @Param("to") OffsetDateTime to,
        Pageable pageable
    );
}
