package co.posinvent.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PickingRequest(
        @NotBlank String pickingNumber,
        @NotNull LocalDate pickingDate,
        @NotNull UUID warehouseId,
        UUID shipmentId,
        UUID salesOrderId,
        String notes,
        @NotNull List<PickingItemRequest> items
) {

    public record PickingItemRequest(
            UUID id,
            @NotNull UUID productId,
            @NotNull UUID warehouseId,
            UUID locationId,
            UUID batchId,
            @NotNull BigDecimal requestedQuantity,
            BigDecimal pickedQuantity,
            String notes
    ) {}
}
