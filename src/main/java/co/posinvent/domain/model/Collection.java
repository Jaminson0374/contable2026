package co.posinvent.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Collection(
        UUID id,
        UUID clientId,
        UUID arId,
        LocalDate dueDate,
        CollectionStatus status,
        LocalDate lastContactDate,
        String contactMethod,
        String contactNotes,
        String assignedTo,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public enum CollectionStatus { PENDING, CONTACTED, PROMISED, PAID, DISPUTED }
}
