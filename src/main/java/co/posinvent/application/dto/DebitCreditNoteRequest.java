package co.posinvent.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record DebitCreditNoteRequest(
        @NotBlank(message = "type is required (DEBIT_NOTE or CREDIT_NOTE)")
        String type,

        @NotNull(message = "supplierId is required")
        UUID supplierId,

        UUID supplierInvoiceId,

        String documentNumber,

        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.01", message = "amount must be > 0")
        BigDecimal amount,

        String reason,

        String reference
) {}
