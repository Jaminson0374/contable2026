package co.posinvent.domain.repository;

import co.posinvent.domain.model.ElectronicInvoiceRequest;
import co.posinvent.domain.model.ElectronicInvoiceResponse;

public interface ElectronicInvoiceProvider {
    ElectronicInvoiceResponse sendInvoice(ElectronicInvoiceRequest request);
    ElectronicInvoiceResponse checkStatus(String cufe);
}
