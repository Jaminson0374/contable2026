package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AuditLog(
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
) {}
