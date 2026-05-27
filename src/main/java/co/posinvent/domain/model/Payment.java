package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record Payment(
        UUID id,
        UUID supplierId,
        BigDecimal amount,
        LocalDate paymentDate,
        String method,
        String reference,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version,
        boolean isAdvance,
        BigDecimal remainingAdvance,
        List<InvoicePayment> invoicePayments
) {
    public Payment {
        if (invoicePayments == null) invoicePayments = List.of();
    }

    public Payment(
            UUID id,
            UUID supplierId,
            BigDecimal amount,
            LocalDate paymentDate,
            String method,
            String reference,
            String notes,
            UUID createdBy,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt,
            Long version,
            List<InvoicePayment> invoicePayments
    ) {
        this(id, supplierId, amount, paymentDate, method, reference, notes,
                createdBy, createdAt, updatedAt, version, false, null, invoicePayments);
    }

    /**
     * Breakdown of how a payment is applied to specific invoices.
     */
    public record InvoicePayment(
            UUID invoiceId,
            BigDecimal appliedAmount
    ) {
        public InvoicePayment {
            if (appliedAmount == null) {
                throw new IllegalArgumentException("appliedAmount must not be null");
            }
        }
    }
}
