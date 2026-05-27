package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record StockTransferItem(
        UUID id,
        UUID transferId,
        UUID productId,
        UUID batchId,
        BigDecimal quantity,
        BigDecimal unitCost
) {}
