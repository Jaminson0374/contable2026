package co.posinvent.domain.model;

public record ElectronicInvoiceResponse(
    String cufe,
    String qrCode,
    String status,
    String providerReference
) {}
