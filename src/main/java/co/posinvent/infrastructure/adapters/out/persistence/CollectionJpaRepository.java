package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Collection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CollectionJpaRepository extends JpaRepository<CollectionEntity, UUID> {

    Page<CollectionEntity> findByClientId(UUID clientId, Pageable pageable);

    Page<CollectionEntity> findByStatus(Collection.CollectionStatus status, Pageable pageable);

    Page<CollectionEntity> findByClientIdAndStatus(UUID clientId, Collection.CollectionStatus status, Pageable pageable);
}
