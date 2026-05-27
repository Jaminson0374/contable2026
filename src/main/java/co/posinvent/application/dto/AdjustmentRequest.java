package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AdjustmentRequest(
        UUID productId,
        UUID batchId,
        UUID warehouseId,
        String adjustmentType,
        BigDecimal quantityAfter,
        String reason
) {}
