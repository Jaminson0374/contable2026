package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record ReceiptLineItem(
        UUID id,
        UUID receiptId,
        UUID productId,
        UUID warehouseId,
        BigDecimal receivedQty,
        BigDecimal actualCost
) {}
