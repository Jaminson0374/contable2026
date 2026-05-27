package co.posinvent.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TransportGuideRequest(
        @NotBlank String guideNumber,
        @NotNull LocalDate issueDate,
        String vehiclePlate,
        String driverName,
        String driverId,
        String originAddress,
        String destinationAddress,
        String carrierName,
        LocalDate estimatedDelivery,
        String notes,
        List<UUID> shipmentIds
) {}
