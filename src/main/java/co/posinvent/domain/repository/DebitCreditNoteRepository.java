package co.posinvent.domain.repository;

import co.posinvent.domain.model.DebitCreditNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DebitCreditNoteRepository {

    DebitCreditNote save(DebitCreditNote note);

    Optional<DebitCreditNote> findById(UUID id);

    List<DebitCreditNote> findBySupplierId(UUID supplierId);

    List<DebitCreditNote> findBySupplierInvoiceId(UUID supplierInvoiceId);

    Page<DebitCreditNote> findAll(Pageable pageable);

    Page<DebitCreditNote> findFiltered(String type, UUID supplierId, Pageable pageable);

    void delete(UUID id);
}
