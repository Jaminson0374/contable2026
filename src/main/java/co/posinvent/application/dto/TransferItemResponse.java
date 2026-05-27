package co.posinvent.application.dto;

import co.posinvent.domain.model.StockTransferItem;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferItemResponse(
        UUID id,
        UUID productId,
        UUID batchId,
        BigDecimal quantity,
        BigDecimal unitCost
) {
    public static TransferItemResponse from(StockTransferItem i) {
        return new TransferItemResponse(i.id(), i.productId(), i.batchId(), i.quantity(), i.unitCost());
    }
}
