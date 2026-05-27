package co.posinvent.domain.repository;

import co.posinvent.domain.model.SaleItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SaleItemRepository {

    SaleItem save(SaleItem item);

    List<SaleItem> findByDocumentId(UUID documentId);

    Optional<SaleItem> findById(UUID id);

    void deleteById(UUID id);
}
