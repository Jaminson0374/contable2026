package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductType;
import co.posinvent.domain.repository.ProductTypeRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class ProductTypeRepositoryAdapter implements ProductTypeRepository {

    private final ProductTypeJpaRepository jpa;
    private final ProductTypeMapper mapper;

    ProductTypeRepositoryAdapter(ProductTypeJpaRepository jpa, ProductTypeMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public List<ProductType> findAllActive() {
        return jpa.findByActiveTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<ProductType> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public ProductType save(ProductType domain) {
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
