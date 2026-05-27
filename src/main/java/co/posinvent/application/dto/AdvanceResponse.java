package co.posinvent.application.dto;

import co.posinvent.domain.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AdvanceResponse(
        UUID id,
        UUID supplierId,
        String supplierName,
        BigDecimal amount,
        BigDecimal remainingAdvance,
        String method,
        String reference,
        OffsetDateTime createdAt
) {
    public static AdvanceResponse from(Payment payment) {
        return new AdvanceResponse(
                payment.id(),
                payment.supplierId(),
                null,
                payment.amount(),
                payment.remainingAdvance(),
                payment.method(),
                payment.reference(),
                payment.createdAt()
        );
    }
}
