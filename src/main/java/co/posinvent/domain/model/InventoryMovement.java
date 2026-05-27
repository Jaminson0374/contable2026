package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record InventoryMovement(
        UUID id,
        UUID productId,
        UUID batchId,
        UUID warehouseId,
        MovementType movementType,
        BigDecimal quantity,
        BigDecimal unitCost,
        BigDecimal previousQty,
        BigDecimal newQty,
        String referenceType,
        UUID referenceId,
        String notes,
        String createdBy,
        OffsetDateTime createdAt
) {
    public InventoryMovement {
        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("La cantidad no puede ser cero.");
        }
    }
}
