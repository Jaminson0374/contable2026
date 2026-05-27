package co.posinvent.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record TransportGuide(
        UUID id,
        String guideNumber,
        LocalDate issueDate,
        String vehiclePlate,
        String driverName,
        String driverId,
        String originAddress,
        String destinationAddress,
        String carrierName,
        LocalDate estimatedDelivery,
        TransportGuideStatus status,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version,
        List<UUID> shipmentIds
) {
    public TransportGuide {
        if (shipmentIds == null) shipmentIds = List.of();
    }
}
