package co.posinvent.application.dto;

import co.posinvent.domain.model.CustomerReceipt;
import co.posinvent.domain.model.ReceiptApplication;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record CustomerReceiptResponse(
        UUID id,
        UUID clientId,
        String clientName,
        BigDecimal amount,
        LocalDate paymentDate,
        String method,
        String reference,
        String notes,
        String createdBy,
        OffsetDateTime createdAt,
        List<ApplicationBreakdown> applications
) {
    public record ApplicationBreakdown(
            UUID id,
            UUID arId,
            BigDecimal appliedAmount
    ) {}

    public static CustomerReceiptResponse from(CustomerReceipt receipt) {
        return new CustomerReceiptResponse(
                receipt.id(),
                receipt.clientId(),
                null,
                receipt.amount(),
                receipt.paymentDate(),
                receipt.method().name(),
                receipt.reference(),
                receipt.notes(),
                receipt.createdBy(),
                receipt.createdAt(),
                receipt.applications().stream()
                        .map(a -> new ApplicationBreakdown(a.id(), a.arId(), a.appliedAmount()))
                        .toList()
        );
    }
}
