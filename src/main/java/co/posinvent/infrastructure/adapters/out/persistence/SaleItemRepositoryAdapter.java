package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.SaleItem;
import co.posinvent.domain.repository.SaleItemRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class SaleItemRepositoryAdapter implements SaleItemRepository {

    private final SaleItemJpaRepository jpa;
    private final SaleItemMapper mapper;
    private final SalesDocumentJpaRepository docJpa;

    SaleItemRepositoryAdapter(SaleItemJpaRepository jpa, SaleItemMapper mapper, SalesDocumentJpaRepository docJpa) {
        this.jpa = jpa;
        this.mapper = mapper;
        this.docJpa = docJpa;
    }

    @Override
    public SaleItem save(SaleItem item) {
        var entity = mapper.toEntity(item);
        entity.setDocument(docJpa.getReferenceById(item.documentId()));
        var saved = jpa.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<SaleItem> findByDocumentId(UUID documentId) {
        return jpa.findByDocumentId(documentId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<SaleItem> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }
}
