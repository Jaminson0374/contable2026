package co.posinvent.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CustomerReceiptRequest(
        @NotNull UUID clientId,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotNull LocalDate paymentDate,
        @NotNull String method,
        String reference,
        String notes,
        @NotNull @NotEmpty @Valid List<ArApplicationInput> applications
) {
    public record ArApplicationInput(
            @NotNull UUID arId,
            @NotNull @DecimalMin("0.01") BigDecimal appliedAmount
    ) {}
}
