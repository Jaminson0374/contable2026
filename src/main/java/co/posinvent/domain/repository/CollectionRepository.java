package co.posinvent.domain.repository;

import co.posinvent.domain.model.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CollectionRepository {

    Collection save(Collection collection);

    Optional<Collection> findById(UUID id);

    Page<Collection> findByClientId(UUID clientId, Pageable pageable);

    Page<Collection> findByStatus(Collection.CollectionStatus status, Pageable pageable);

    Page<Collection> findByClientIdAndStatus(UUID clientId, Collection.CollectionStatus status, Pageable pageable);

    Page<Collection> findAll(Pageable pageable);
}
