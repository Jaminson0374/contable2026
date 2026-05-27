package co.posinvent.domain.repository;

import co.posinvent.domain.model.InvoiceStatus;
import co.posinvent.domain.model.SupplierInvoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface SupplierInvoiceRepository {

    SupplierInvoice save(SupplierInvoice invoice);

    Optional<SupplierInvoice> findById(UUID id);

    Page<SupplierInvoice> findAll(Pageable pageable);

    Page<SupplierInvoice> findBySupplierId(UUID supplierId, Pageable pageable);

    Page<SupplierInvoice> findByStatus(InvoiceStatus status, Pageable pageable);

    Page<SupplierInvoice> findBySupplierIdAndStatus(UUID supplierId, InvoiceStatus status, Pageable pageable);

    Optional<SupplierInvoice> findByInvoiceNumberAndSupplierId(String invoiceNumber, UUID supplierId);
}
