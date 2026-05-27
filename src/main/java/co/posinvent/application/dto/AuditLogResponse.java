package co.posinvent.application.dto;

import co.posinvent.domain.model.AuditLog;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AuditLogResponse(
    UUID id,
    String entityType,
    UUID entityId,
    String action,
    String fieldName,
    String oldValue,
    String newValue,
    UUID userId,
    String ipAddress,
    OffsetDateTime createdAt
) {
    public static AuditLogResponse from(AuditLog log) {
        return new AuditLogResponse(
                log.id(),
                log.entityType(),
                log.entityId(),
                log.action(),
                log.fieldName(),
                log.oldValue(),
                log.newValue(),
                log.userId(),
                log.ipAddress(),
                log.createdAt()
        );
    }
}
