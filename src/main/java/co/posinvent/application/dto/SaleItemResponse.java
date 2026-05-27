package co.posinvent.application.dto;

import co.posinvent.domain.model.SaleItem;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleItemResponse(
        UUID id,
        UUID documentId,
        UUID productId,
        String productName,
        BigDecimal quantity,
        BigDecimal unitPrice,
        String taxType,
        BigDecimal taxRate,
        BigDecimal taxAmount,
        BigDecimal subtotal,
        int lineNumber,
        UUID batchId
) {
    public static SaleItemResponse from(SaleItem item) {
        return new SaleItemResponse(
                item.id(),
                item.documentId(),
                item.productId(),
                null,
                item.quantity(),
                item.unitPrice(),
                item.taxType(),
                item.taxRate(),
                item.taxAmount(),
                item.subtotal(),
                item.lineNumber(),
                item.batchId()
        );
    }
}
