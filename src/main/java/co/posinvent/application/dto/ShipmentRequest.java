package co.posinvent.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ShipmentRequest(
        @NotBlank String shipmentNumber,
        @NotNull LocalDate shipmentDate,
        String carrierName,
        String vehiclePlate,
        String driverName,
        UUID transportGuideId,
        String notes,
        @NotNull List<ShipmentItemRequest> items
) {

    public record ShipmentItemRequest(
            UUID id,
            @NotNull UUID productId,
            UUID pickingId,
            UUID batchId,
            @NotNull BigDecimal quantity,
            String notes
    ) {}
}
