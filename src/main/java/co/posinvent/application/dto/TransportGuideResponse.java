package co.posinvent.application.dto;

import co.posinvent.domain.model.TransportGuide;
import co.posinvent.domain.model.TransportGuideStatus;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record TransportGuideResponse(
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
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<UUID> shipmentIds
) {

    public static TransportGuideResponse from(TransportGuide g) {
        return new TransportGuideResponse(
                g.id(), g.guideNumber(), g.issueDate(), g.vehiclePlate(),
                g.driverName(), g.driverId(), g.originAddress(),
                g.destinationAddress(), g.carrierName(), g.estimatedDelivery(),
                g.status(), g.notes(), g.createdAt(), g.updatedAt(),
                g.shipmentIds());
    }
}
