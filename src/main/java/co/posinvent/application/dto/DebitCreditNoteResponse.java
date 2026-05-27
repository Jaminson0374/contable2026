package co.posinvent.application.dto;

import co.posinvent.domain.model.DebitCreditNote;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record DebitCreditNoteResponse(
        UUID id,
        String type,
        UUID supplierId,
        String supplierName,
        UUID supplierInvoiceId,
        String documentNumber,
        BigDecimal amount,
        String reason,
        String reference,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static DebitCreditNoteResponse from(DebitCreditNote note, String supplierName) {
        return new DebitCreditNoteResponse(
                note.id(),
                note.type(),
                note.supplierId(),
                supplierName,
                note.supplierInvoiceId(),
                note.documentNumber(),
                note.amount(),
                note.reason(),
                note.reference(),
                note.createdBy(),
                note.createdAt(),
                note.updatedAt()
        );
    }
}
