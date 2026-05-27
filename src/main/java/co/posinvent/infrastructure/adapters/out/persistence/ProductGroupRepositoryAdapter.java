package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductGroup;
import co.posinvent.domain.repository.ProductGroupRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class ProductGroupRepositoryAdapter implements ProductGroupRepository {

    private final ProductGroupJpaRepository jpa;
    private final ProductGroupMapper mapper;

    ProductGroupRepositoryAdapter(ProductGroupJpaRepository jpa, ProductGroupMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public List<ProductGroup> findAllActive() {
        return jpa.findByActiveTrueOrderByNameAsc().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<ProductGroup> findByCategoryId(UUID categoryId) {
        return jpa.findByCategoryIdAndActiveTrue(categoryId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<ProductGroup> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public ProductGroup save(ProductGroup domain) {
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
