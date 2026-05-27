package co.posinvent.application.dto;

import co.posinvent.domain.model.AccountsReceivable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AccountsReceivableResponse(
        UUID id,
        UUID clientId,
        String clientName,
        UUID documentId,
        String documentNumber,
        BigDecimal totalAmount,
        BigDecimal paidAmount,
        BigDecimal outstanding,
        LocalDate dueDate,
        String status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        BigDecimal interestRate,
        BigDecimal interestAmount,
        LocalDate lastInterestCalcDate
) {
    public static AccountsReceivableResponse from(AccountsReceivable ar) {
        return new AccountsReceivableResponse(
                ar.id(),
                ar.clientId(),
                null,
                ar.documentId(),
                null,
                ar.totalAmount(),
                ar.paidAmount(),
                ar.outstanding(),
                ar.dueDate(),
                ar.status().name(),
                ar.createdAt(),
                ar.updatedAt(),
                ar.interestRate(),
                ar.interestAmount(),
                ar.lastInterestCalcDate()
        );
    }
}
