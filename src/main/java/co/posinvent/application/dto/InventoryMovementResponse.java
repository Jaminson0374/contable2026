package co.posinvent.application.dto;

import co.posinvent.domain.model.MovementType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record InventoryMovementResponse(
        UUID id,
        UUID productId,
        UUID batchId,
        UUID warehouseId,
        String movementType,
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
    public static InventoryMovementResponse from(co.posinvent.domain.model.InventoryMovement m) {
        return new InventoryMovementResponse(
                m.id(),
                m.productId(),
                m.batchId(),
                m.warehouseId(),
                m.movementType().name(),
                m.quantity(),
                m.unitCost(),
                m.previousQty(),
                m.newQty(),
                m.referenceType(),
                m.referenceId(),
                m.notes(),
                m.createdBy(),
                m.createdAt()
        );
    }
}
