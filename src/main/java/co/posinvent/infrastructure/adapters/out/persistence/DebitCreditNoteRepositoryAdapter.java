package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.DebitCreditNote;
import co.posinvent.domain.repository.DebitCreditNoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class DebitCreditNoteRepositoryAdapter implements DebitCreditNoteRepository {

    private final DebitCreditNoteJpaRepository jpa;
    private final DebitCreditNoteMapper mapper;

    DebitCreditNoteRepositoryAdapter(DebitCreditNoteJpaRepository jpa, DebitCreditNoteMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public DebitCreditNote save(DebitCreditNote note) {
        var entity = mapper.toEntity(note);
        if (note.id() != null) {
            var existing = jpa.findById(note.id());
            if (existing.isPresent()) {
                entity = existing.get();
                entity.setType(note.type());
                entity.setSupplierId(note.supplierId());
                entity.setSupplierInvoiceId(note.supplierInvoiceId());
                entity.setDocumentNumber(note.documentNumber());
                entity.setAmount(note.amount());
                entity.setReason(note.reason());
                entity.setReference(note.reference());
            }
        }
        return mapper.toDomain(jpa.save(entity));
    }

    @Override
    public Optional<DebitCreditNote> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<DebitCreditNote> findBySupplierId(UUID supplierId) {
        return jpa.findBySupplierId(supplierId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<DebitCreditNote> findBySupplierInvoiceId(UUID supplierInvoiceId) {
        return jpa.findBySupplierInvoiceId(supplierInvoiceId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Page<DebitCreditNote> findAll(Pageable pageable) {
        return jpa.findAll(pageable).map(mapper::toDomain);
    }

    @Override
    public Page<DebitCreditNote> findFiltered(String type, UUID supplierId, Pageable pageable) {
        return jpa.findFiltered(type, supplierId, pageable).map(mapper::toDomain);
    }

    @Override
    public void delete(UUID id) {
        jpa.deleteById(id);
    }
}
