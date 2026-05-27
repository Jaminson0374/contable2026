package co.posinvent.application.usecase;

import java.math.BigDecimal;

public record PriceResult(
        BigDecimal unitPrice,
        String taxType,
        BigDecimal taxRate,
        BigDecimal taxAmount
) {}
