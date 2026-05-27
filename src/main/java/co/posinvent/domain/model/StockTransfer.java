package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record StockTransfer(
        UUID id,
        UUID sourceWarehouseId,
        UUID targetWarehouseId,
        TransferStatus status,
        String notes,
        String createdBy,
        OffsetDateTime createdAt,
        String confirmedBy,
        OffsetDateTime confirmedAt,
        List<StockTransferItem> items
) {
    public StockTransfer {
        if (items == null) items = List.of();
    }
}
