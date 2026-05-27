package co.posinvent.domain.repository;

import co.posinvent.domain.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface AuditLogRepository {
    AuditLog save(AuditLog auditLog);
    Page<AuditLog> findFiltered(
            String entityType,
            UUID userId,
            String action,
            OffsetDateTime from,
            OffsetDateTime to,
            Pageable pageable
    );
}
