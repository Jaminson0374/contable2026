package co.posinvent.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record PaymentRequest(
        @NotNull UUID supplierId,
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotNull LocalDate paymentDate,
        @NotNull String method,
        String reference,
        String notes,
        @NotNull @NotEmpty List<InvoicePaymentInput> invoicePayments
) {
    public record InvoicePaymentInput(
            @NotNull UUID invoiceId,
            @NotNull @DecimalMin("0.01") BigDecimal appliedAmount
    ) {}
}
