package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record StockAdjustment(
        UUID id,
        UUID productId,
        UUID batchId,
        UUID warehouseId,
        AdjustmentType adjustmentType,
        BigDecimal quantityBefore,
        BigDecimal quantityAfter,
        BigDecimal unitCost,
        String reason,
        String createdBy,
        OffsetDateTime createdAt
) {}
