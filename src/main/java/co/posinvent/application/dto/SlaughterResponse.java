package co.posinvent.application.dto;

import co.posinvent.domain.model.Slaughter.SlaughterSourceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record SlaughterResponse(
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
        OffsetDateTime createdAt
) {
    public static SlaughterResponse from(co.posinvent.domain.model.Slaughter s) {
        return new SlaughterResponse(
                s.id(), s.animalId(), s.carcassWeight(), s.yieldPercentage(),
                s.slaughterDate(), s.invimaPlant(), s.inspectorId(), s.sourceType(),
                s.justification(), s.purchaseCost(), s.batchId(), s.notes(), s.createdAt()
        );
    }
}
