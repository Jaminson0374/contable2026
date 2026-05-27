package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public record ElectronicInvoice(
    UUID id,
    UUID salesDocumentId,
    UUID sourceDocumentId,
    String cufe,
    String qrCode,
    Map<String, Object> providerResponse,
    ElectronicInvoiceStatus status,
    OffsetDateTime sentAt,
    OffsetDateTime responseAt,
    OffsetDateTime createdAt
) {}
