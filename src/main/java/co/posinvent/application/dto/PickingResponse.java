package co.posinvent.application.dto;

import co.posinvent.domain.model.Picking;
import co.posinvent.domain.model.PickingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record PickingResponse(
        UUID id,
        String pickingNumber,
        LocalDate pickingDate,
        UUID warehouseId,
        UUID shipmentId,
        UUID salesOrderId,
        PickingStatus status,
        String notes,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<PickingItemResponse> items
) {

    public static PickingResponse from(Picking p) {
        var items = p.items() == null ? List.<PickingItemResponse>of() : p.items().stream()
                .map(i -> new PickingItemResponse(
                        i.id(), i.pickingId(), i.productId(), i.warehouseId(),
                        i.locationId(), i.batchId(), i.requestedQuantity(),
                        i.pickedQuantity(), i.notes()))
                .toList();
        return new PickingResponse(
                p.id(), p.pickingNumber(), p.pickingDate(), p.warehouseId(),
                p.shipmentId(), p.salesOrderId(), p.status(), p.notes(),
                p.createdAt(), p.updatedAt(), items);
    }

    public record PickingItemResponse(
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
