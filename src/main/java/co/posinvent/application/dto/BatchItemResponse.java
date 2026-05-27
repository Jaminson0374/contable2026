package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record BatchItemResponse(
        UUID componentId,
        String componentName,
        BigDecimal plannedQuantity,
        BigDecimal actualQuantity,
        BigDecimal unitCost,
        BigDecimal totalCost,
        UUID kardexMovementId
) {}
