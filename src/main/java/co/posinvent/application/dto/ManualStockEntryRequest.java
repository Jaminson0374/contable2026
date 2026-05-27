package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ManualStockEntryRequest(
        UUID productId,
        UUID batchId,
        UUID warehouseId,
        BigDecimal quantity,
        BigDecimal unitCost,
        String notes
) {}
