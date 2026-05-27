package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductModel;
import co.posinvent.domain.repository.ProductModelRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class ProductModelRepositoryAdapter implements ProductModelRepository {

    private final ProductModelJpaRepository jpa;
    private final ProductModelMapper mapper;

    ProductModelRepositoryAdapter(ProductModelJpaRepository jpa, ProductModelMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public List<ProductModel> findAllActive() {
        return jpa.findByActiveTrueOrderByNameAsc().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<ProductModel> findByBrandId(UUID brandId) {
        return jpa.findByBrandIdAndActiveTrue(brandId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<ProductModel> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public ProductModel save(ProductModel domain) {
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
