package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Animal(
        UUID id,
        String icaLotNumber,
        UUID supplierId,
        Species species,
        BigDecimal liveWeight,
        LocalDate receptionDate,
        AnimalStatus status,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public enum Species { PORCINO, BOVINO, OVINO }

    public enum AnimalStatus { RECEIVED, IN_SLAUGHTER, SLAUGHTERED }

    /**
     * Solo un animal en estado RECEIVED puede ser procesado para faena.
     */
    public boolean isSlaughterable() {
        return status == AnimalStatus.RECEIVED;
    }
}
