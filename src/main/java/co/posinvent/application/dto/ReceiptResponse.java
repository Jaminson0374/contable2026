package co.posinvent.application.dto;

import co.posinvent.domain.model.Receipt;
import co.posinvent.domain.model.ReceiptStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record ReceiptResponse(
        UUID id,
        String receiptNumber,
        LocalDate receiptDate,
        UUID supplierId,
        UUID purchaseOrderId,
        UUID warehouseId,
        ReceiptStatus status,
        String notes,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        List<ReceiptItemResponse> items
) {

    public static ReceiptResponse from(Receipt r) {
        var items = r.items() == null ? List.<ReceiptItemResponse>of() : r.items().stream()
                .map(i -> new ReceiptItemResponse(
                        i.id(), i.receiptId(), i.productId(), i.warehouseId(),
                        i.batchId(), i.orderedQuantity(), i.receivedQuantity(),
                        i.unitCost(), i.notes()))
                .toList();
        return new ReceiptResponse(
                r.id(), r.receiptNumber(), r.receiptDate(), r.supplierId(),
                r.purchaseOrderId(), r.warehouseId(), r.status(), r.notes(),
                r.createdAt(), r.updatedAt(), items);
    }

    public record ReceiptItemResponse(
            UUID id,
            UUID receiptId,
            UUID productId,
            UUID warehouseId,
            UUID batchId,
            BigDecimal orderedQuantity,
            BigDecimal receivedQuantity,
            BigDecimal unitCost,
            String notes
    ) {}
}
