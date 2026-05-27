package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record CustomerReceipt(
        UUID id,
        UUID clientId,
        BigDecimal amount,
        LocalDate paymentDate,
        PaymentMethod method,
        String reference,
        String notes,
        String createdBy,
        OffsetDateTime createdAt,
        List<ReceiptApplication> applications
) {
    public CustomerReceipt {
        if (applications == null) applications = List.of();
    }

    public enum PaymentMethod { CASH, TRANSFER, CARD, CHECK }
}
