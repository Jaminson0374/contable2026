package co.posinvent.application.dto;

import co.posinvent.domain.model.StockTransfer;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record TransferResponse(
        UUID id,
        UUID sourceWarehouseId,
        UUID targetWarehouseId,
        String status,
        String notes,
        String createdBy,
        OffsetDateTime createdAt,
        String confirmedBy,
        OffsetDateTime confirmedAt,
        List<TransferItemResponse> items
) {
    public static TransferResponse from(StockTransfer t) {
        return new TransferResponse(
                t.id(), t.sourceWarehouseId(), t.targetWarehouseId(),
                t.status().name(), t.notes(),
                t.createdBy(), t.createdAt(),
                t.confirmedBy(), t.confirmedAt(),
                t.items().stream().map(TransferItemResponse::from).toList()
        );
    }
}
