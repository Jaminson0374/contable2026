package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record Shipment(
        UUID id,
        String shipmentNumber,
        LocalDate shipmentDate,
        String carrierName,
        String vehiclePlate,
        String driverName,
        UUID transportGuideId,
        ShipmentStatus status,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version,
        List<ShipmentItem> items
) {
    public Shipment {
        if (items == null) items = List.of();
    }

    public record ShipmentItem(
            UUID id,
            UUID shipmentId,
            UUID productId,
            UUID pickingId,
            UUID batchId,
            BigDecimal quantity,
            String notes
    ) {}
}
