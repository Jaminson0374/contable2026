package co.posinvent.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ApplyAdvanceRequest(
        @NotNull UUID advancePaymentId,
        @NotNull UUID invoiceId,
        @NotNull @DecimalMin("0.01") BigDecimal appliedAmount
) {}
