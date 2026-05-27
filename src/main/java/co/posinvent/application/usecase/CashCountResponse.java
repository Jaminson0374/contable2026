package co.posinvent.application.usecase;

import java.math.BigDecimal;

public record CashCountResponse(
        BigDecimal expectedTotal,
        BigDecimal actualTotal,
        BigDecimal difference,
        int invoiceCount
) {}
