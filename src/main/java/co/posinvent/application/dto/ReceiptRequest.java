package co.posinvent.application.dto;

import co.posinvent.domain.model.ReceiptStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ReceiptRequest(
        @NotBlank String receiptNumber,
        @NotNull LocalDate receiptDate,
        UUID supplierId,
        UUID purchaseOrderId,
        @NotNull UUID warehouseId,
        String notes,
        @NotNull List<ReceiptItemRequest> items
) {

    public record ReceiptItemRequest(
            UUID id,
            @NotNull UUID productId,
            @NotNull UUID warehouseId,
            UUID batchId,
            BigDecimal orderedQuantity,
            @NotNull BigDecimal receivedQuantity,
            @NotNull BigDecimal unitCost,
            String notes
    ) {}
}
