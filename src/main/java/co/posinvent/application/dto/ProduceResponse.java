package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProduceResponse(
        UUID batchId,
        String productName,
        BigDecimal quantityProduced,
        BigDecimal mpd,
        BigDecimal mod,
        BigDecimal cif,
        BigDecimal totalCost,
        BigDecimal unitCost,
        BigDecimal shrinkage,
        List<BatchItemResponse> items
) {}
