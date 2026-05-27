package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record SaleItem(
        UUID id,
        UUID documentId,
        UUID productId,
        BigDecimal quantity,
        BigDecimal unitPrice,
        String taxType,
        BigDecimal taxRate,
        BigDecimal taxAmount,
        BigDecimal subtotal,
        int lineNumber,
        UUID batchId
) {}
