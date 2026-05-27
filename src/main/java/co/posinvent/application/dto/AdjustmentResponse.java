package co.posinvent.application.dto;

import co.posinvent.domain.model.StockAdjustment;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AdjustmentResponse(
        UUID id,
        UUID productId,
        UUID batchId,
        UUID warehouseId,
        String adjustmentType,
        BigDecimal quantityBefore,
        BigDecimal quantityAfter,
        BigDecimal unitCost,
        String reason,
        String createdBy,
        OffsetDateTime createdAt
) {
    public static AdjustmentResponse from(StockAdjustment a) {
        return new AdjustmentResponse(
                a.id(), a.productId(), a.batchId(), a.warehouseId(),
                a.adjustmentType().name(),
                a.quantityBefore(), a.quantityAfter(),
                a.unitCost(), a.reason(),
                a.createdBy(), a.createdAt()
        );
    }
}
