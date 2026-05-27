package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DianSyncQueueItem(
    UUID id,
    UUID electronicInvoiceId,
    Integer attemptCount,
    Integer maxAttempts,
    OffsetDateTime nextAttemptAt,
    String lastError,
    SyncStatus status,
    OffsetDateTime createdAt
) {}
