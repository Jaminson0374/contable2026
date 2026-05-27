package co.posinvent.application.dto;

import co.posinvent.domain.model.ElectronicInvoice;
import co.posinvent.domain.model.ElectronicInvoiceStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ElectronicInvoiceResponse(
    UUID id,
    UUID salesDocumentId,
    UUID sourceDocumentId,
    String cufe,
    String qrCode,
    ElectronicInvoiceStatus status,
    OffsetDateTime sentAt,
    OffsetDateTime responseAt,
    OffsetDateTime createdAt
) {
    public static ElectronicInvoiceResponse from(ElectronicInvoice e) {
        return new ElectronicInvoiceResponse(
            e.id(), e.salesDocumentId(), e.sourceDocumentId(),
            e.cufe(), e.qrCode(), e.status(),
            e.sentAt(), e.responseAt(), e.createdAt()
        );
    }
}
