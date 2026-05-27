package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Slaughter(
        UUID id,
        UUID animalId,
        BigDecimal carcassWeight,
        BigDecimal yieldPercentage,
        LocalDate slaughterDate,
        String invimaPlant,
        UUID inspectorId,
        SlaughterSourceType sourceType,
        String justification,
        BigDecimal purchaseCost,
        UUID batchId,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public enum SlaughterSourceType { MANUAL, AUTOMATIC }
}
