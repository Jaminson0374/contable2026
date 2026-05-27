package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Collection;
import co.posinvent.domain.repository.CollectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
class CollectionRepositoryAdapter implements CollectionRepository {

    private final CollectionJpaRepository jpa;
    private final CollectionMapper mapper;

    CollectionRepositoryAdapter(CollectionJpaRepository jpa, CollectionMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Collection save(Collection collection) {
        return mapper.toDomain(jpa.save(mapper.toEntity(collection)));
    }

    @Override
    public Optional<Collection> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Collection> findByClientId(UUID clientId, Pageable pageable) {
        return jpa.findByClientId(clientId, pageable).map(mapper::toDomain);
    }

    @Override
    public Page<Collection> findByStatus(Collection.CollectionStatus status, Pageable pageable) {
        return jpa.findByStatus(status, pageable).map(mapper::toDomain);
    }

    @Override
    public Page<Collection> findByClientIdAndStatus(UUID clientId, Collection.CollectionStatus status, Pageable pageable) {
        return jpa.findByClientIdAndStatus(clientId, status, pageable).map(mapper::toDomain);
    }

    @Override
    public Page<Collection> findAll(Pageable pageable) {
        return jpa.findAll(pageable).map(mapper::toDomain);
    }
}
