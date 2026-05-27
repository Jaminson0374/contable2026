package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record Picking(
        UUID id,
        String pickingNumber,
        LocalDate pickingDate,
        UUID warehouseId,
        UUID shipmentId,
        UUID salesOrderId,
        PickingStatus status,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version,
        List<PickingItem> items
) {
    public Picking {
        if (items == null) items = List.of();
    }

    public record PickingItem(
            UUID id,
            UUID pickingId,
            UUID productId,
            UUID warehouseId,
            UUID locationId,
            UUID batchId,
            BigDecimal requestedQuantity,
            BigDecimal pickedQuantity,
            String notes
    ) {}
}
