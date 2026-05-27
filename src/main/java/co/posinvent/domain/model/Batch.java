package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Batch(
        UUID id,
        UUID supplierId,
        UUID warehouseId,
        LocalDate entryDate,
        BigDecimal initialWeight,
        BigDecimal purchaseCost,
        BatchStatus status,
        String notes,
        LocalDate expirationDate,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        UUID sourceReceiptId,
        UUID ocId
) {
    public enum BatchStatus { OPEN, PROCESSING, CLOSED }

    public boolean isMutable() {
        return status == BatchStatus.OPEN;
    }
}
