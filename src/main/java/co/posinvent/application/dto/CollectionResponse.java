package co.posinvent.application.dto;

import co.posinvent.domain.model.Collection.CollectionStatus;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public record CollectionResponse(
        String id,
        String clientId,
        String clientName,
        String arId,
        String documentNumber,
        LocalDate dueDate,
        CollectionStatus status,
        LocalDate lastContactDate,
        String contactMethod,
        String contactNotes,
        String assignedTo,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
