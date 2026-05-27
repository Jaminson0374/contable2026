package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductCategory;
import co.posinvent.domain.repository.ProductCategoryRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class ProductCategoryRepositoryAdapter implements ProductCategoryRepository {

    private final ProductCategoryJpaRepository jpa;
    private final ProductCategoryMapper mapper;

    ProductCategoryRepositoryAdapter(ProductCategoryJpaRepository jpa, ProductCategoryMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public List<ProductCategory> findAllActive() {
        return jpa.findByActiveTrue().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<ProductCategory> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public ProductCategory save(ProductCategory domain) {
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
