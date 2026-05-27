package co.posinvent.application.dto;

import co.posinvent.domain.model.InventoryStock;

import java.math.BigDecimal;
import java.util.UUID;

public record StockResponse(
        UUID id,
        UUID productId,
        UUID batchId,
        UUID warehouseId,
        BigDecimal currentQuantity,
        BigDecimal committedQuantity,
        BigDecimal availableQuantity,
        BigDecimal unitCost
) {
    public static StockResponse from(InventoryStock s) {
        return new StockResponse(
                s.id(), s.productId(), s.batchId(), s.warehouseId(),
                s.currentQuantity(), s.committedQuantity(),
                s.availableQuantity(), s.unitCost()
        );
    }
}
