package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record ProfitabilityRow(
        UUID productId,
        String productName,
        String productCode,
        BigDecimal totalRevenue,
        BigDecimal totalCogs,
        BigDecimal grossMargin,
        BigDecimal marginPercent
) {}
