package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductFormula(
        UUID id,
        UUID parentProductId,
        UUID componentProductId,
        BigDecimal quantity,
        UUID unitOfMeasureId,
        int sequenceNumber,
        String notes,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
