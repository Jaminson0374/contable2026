package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record FormulaComponentResponse(
        UUID componentId,
        String componentName,
        BigDecimal requiredQuantity,
        String unitOfMeasure,
        BigDecimal currentStock,
        BigDecimal estimatedCost
) {}
