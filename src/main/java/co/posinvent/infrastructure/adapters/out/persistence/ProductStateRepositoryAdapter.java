package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductState;
import co.posinvent.domain.repository.ProductStateRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class ProductStateRepositoryAdapter implements ProductStateRepository {

    private final ProductStateJpaRepository jpa;
    private final ProductStateMapper mapper;

    ProductStateRepositoryAdapter(ProductStateJpaRepository jpa, ProductStateMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public List<ProductState> findAllActive() {
        return jpa.findByActiveTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<ProductState> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public ProductState save(ProductState domain) {
        return mapper.toDomain(jpa.save(mapper.toEntity(domain)));
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByName(name);
    }

    @Override
    public boolean existsByNameAndIdNot(String name, UUID id) {
        return jpa.existsByNameAndIdNot(name, id);
    }
}
