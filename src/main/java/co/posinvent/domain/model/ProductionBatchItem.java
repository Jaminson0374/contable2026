package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductionBatchItem(
        UUID id,
        UUID batchId,
        UUID componentProductId,
        BigDecimal plannedQuantity,
        BigDecimal actualQuantity,
        BigDecimal unitCost,
        BigDecimal totalCost,
        UUID kardexMovementId
) {}
