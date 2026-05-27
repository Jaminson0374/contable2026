package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record DebitCreditNote(
        UUID id,
        String type,
        UUID supplierId,
        UUID supplierInvoiceId,
        String documentNumber,
        BigDecimal amount,
        String reason,
        String reference,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version
) {
    public DebitCreditNote {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be > 0");
        }
    }

    public boolean isDebitNote() {
        return "DEBIT_NOTE".equals(type);
    }

    public boolean isCreditNote() {
        return "CREDIT_NOTE".equals(type);
    }
}
