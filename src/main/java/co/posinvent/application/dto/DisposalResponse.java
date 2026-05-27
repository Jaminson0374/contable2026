package co.posinvent.application.dto;

import co.posinvent.domain.model.StockDisposal;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record DisposalResponse(
        UUID id, UUID productId, UUID batchId, UUID warehouseId,
        String disposalType, BigDecimal quantity, BigDecimal unitCost,
        String reason, String createdBy, OffsetDateTime createdAt
) {
    public static DisposalResponse from(StockDisposal d) {
        return new DisposalResponse(d.id(), d.productId(), d.batchId(), d.warehouseId(),
                d.disposalType().name(), d.quantity(), d.unitCost(), d.reason(), d.createdBy(), d.createdAt());
    }
}
