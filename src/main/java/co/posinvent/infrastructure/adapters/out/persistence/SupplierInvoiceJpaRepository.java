package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface SupplierInvoiceJpaRepository extends JpaRepository<SupplierInvoiceEntity, UUID> {

    Page<SupplierInvoiceEntity> findBySupplierId(UUID supplierId, Pageable pageable);

    Page<SupplierInvoiceEntity> findByStatus(InvoiceStatus status, Pageable pageable);

    Page<SupplierInvoiceEntity> findBySupplierIdAndStatus(UUID supplierId, InvoiceStatus status,
                                                               Pageable pageable);

    Optional<SupplierInvoiceEntity> findByInvoiceNumberAndSupplierId(String invoiceNumber,
                                                                      UUID supplierId);
}
