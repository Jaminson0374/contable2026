package co.posinvent.application.usecase;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CashCountRequest(
        @NotNull BigDecimal totalCash,
        @NotNull BigDecimal totalCard,
        @NotNull BigDecimal totalTransfer,
        @NotNull BigDecimal totalCredit,
        String notes
) {}
