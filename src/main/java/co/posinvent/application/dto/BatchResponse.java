package co.posinvent.application.dto;

import co.posinvent.domain.model.Batch;
import co.posinvent.domain.model.Batch.BatchStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record BatchResponse(
        UUID id,
        UUID supplierId,
        UUID warehouseId,
        LocalDate entryDate,
        BigDecimal initialWeight,
        BigDecimal purchaseCost,
        BatchStatus status,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt
) {
    public static BatchResponse from(Batch b) {
        return new BatchResponse(
                b.id(), b.supplierId(), b.warehouseId(), b.entryDate(),
                b.initialWeight(), b.purchaseCost(), b.status(),
                b.notes(), b.createdBy(), b.createdAt()
        );
    }
}
