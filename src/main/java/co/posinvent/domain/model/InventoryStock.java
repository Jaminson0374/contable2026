package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record InventoryStock(
        UUID id,
        UUID productId,
        UUID batchId,
        UUID warehouseId,
        BigDecimal currentQuantity,
        BigDecimal committedQuantity,
        BigDecimal unitCost,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    // Cálculo de disponibilidad según sdd_domain_logic.md §5:
    // Available_Stock = Current_Stock - Committed_Stock
    public BigDecimal availableQuantity() {
        return currentQuantity.subtract(committedQuantity);
    }

    public boolean hasStock(BigDecimal required) {
        return availableQuantity().compareTo(required) >= 0;
    }
}
