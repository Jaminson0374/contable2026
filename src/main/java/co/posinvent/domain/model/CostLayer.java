package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CostLayer(
        UUID id,
        UUID productId,
        UUID batchId,
        UUID warehouseId,
        BigDecimal remainingQuantity,
        BigDecimal unitCost,
        OffsetDateTime entryDate,
        UUID sourceMovementId
) {}
