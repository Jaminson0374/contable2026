package co.posinvent.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record Receipt(
        UUID id,
        String receiptNumber,
        LocalDate receiptDate,
        UUID supplierId,
        UUID purchaseOrderId,
        UUID warehouseId,
        ReceiptStatus status,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version,
        List<ReceiptItem> items
) {
    public Receipt {
        if (items == null) items = List.of();
    }

    public record ReceiptItem(
            UUID id,
            UUID receiptId,
            UUID productId,
            UUID warehouseId,
            UUID batchId,
            java.math.BigDecimal orderedQuantity,
            java.math.BigDecimal receivedQuantity,
            java.math.BigDecimal unitCost,
            String notes
    ) {}
}
