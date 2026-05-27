package co.posinvent.application.dto;

import co.posinvent.domain.model.Shipment;
import co.posinvent.domain.model.ShipmentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ShipmentResponse(
        UUID id,
        String shipmentNumber,
        LocalDate shipmentDate,
        String carrierName,
        String vehiclePlate,
        String driverName,
        UUID transportGuideId,
        ShipmentStatus status,
        String notes,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<ShipmentItemResponse> items
) {

    public static ShipmentResponse from(Shipment s) {
        var items = s.items() == null ? List.<ShipmentItemResponse>of() : s.items().stream()
                .map(i -> new ShipmentItemResponse(
                        i.id(), i.shipmentId(), i.productId(), i.pickingId(),
                        i.batchId(), i.quantity(), i.notes()))
                .toList();
        return new ShipmentResponse(
                s.id(), s.shipmentNumber(), s.shipmentDate(), s.carrierName(),
                s.vehiclePlate(), s.driverName(), s.transportGuideId(),
                s.status(), s.notes(), s.createdAt(), s.updatedAt(), items);
    }

    public record ShipmentItemResponse(
            UUID id,
            UUID shipmentId,
            UUID productId,
            UUID pickingId,
            UUID batchId,
            BigDecimal quantity,
            String notes
    ) {}
}
