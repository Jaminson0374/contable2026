package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ManualStockExitRequest(
        UUID productId,
        UUID batchId,
        UUID warehouseId,
        BigDecimal quantity,
        String reason
) {}
