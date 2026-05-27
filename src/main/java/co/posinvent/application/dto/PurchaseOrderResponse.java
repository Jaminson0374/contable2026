package co.posinvent.application.dto;

import co.posinvent.domain.model.PurchaseOrder;
import co.posinvent.domain.model.PurchaseLineItem;
import co.posinvent.domain.model.PurchaseOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record PurchaseOrderResponse(
        UUID id,
        UUID supplierId,
        String supplierName,
        PurchaseOrderStatus status,
        LocalDate orderDate,
        String documentNumber,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt,
        List<LineItemResponse> lines
) {
    public record LineItemResponse(
            UUID id,
            UUID productId,
            String productName,
            BigDecimal orderedQty,
            BigDecimal receivedQty,
            BigDecimal unitCost,
            UUID warehouseId,
            String warehouseName,
            int lineNumber
    ) {}

    public static PurchaseOrderResponse from(PurchaseOrder po) {
        return new PurchaseOrderResponse(
                po.id(),
                po.supplierId(),
                null,
                po.status(),
                po.orderDate(),
                po.documentNumber(),
                po.notes(),
                po.createdBy(),
                po.createdAt(),
                po.lines().stream()
                        .map(PurchaseOrderResponse::fromLineItem)
                        .toList()
        );
    }

    private static LineItemResponse fromLineItem(PurchaseLineItem li) {
        return new LineItemResponse(
                li.id(),
                li.productId(),
                null,
                li.orderedQty(),
                li.receivedQty(),
                li.unitCost(),
                li.warehouseId(),
                null,
                li.lineNumber()
        );
    }
}
