package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record SalesByProductRow(
        UUID productId,
        String productName,
        String productCode,
        String productGroup,
        BigDecimal totalQuantity,
        BigDecimal totalRevenue,
        int transactionCount
) {}
