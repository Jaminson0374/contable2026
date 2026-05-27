package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductPresentation;
import co.posinvent.domain.repository.ProductPresentationRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class ProductPresentationRepositoryAdapter implements ProductPresentationRepository {

    private final ProductPresentationJpaRepository jpa;
    private final ProductPresentationMapper mapper;

    ProductPresentationRepositoryAdapter(ProductPresentationJpaRepository jpa, ProductPresentationMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public ProductPresentation save(ProductPresentation presentation) {
        var entity = mapper.toEntity(presentation);
        return mapper.toDomain(jpa.save(entity));
    }

    @Override
    public Optional<ProductPresentation> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ProductPresentation> findByProductId(UUID productId) {
        return mapper.toDomainList(jpa.findByProductIdOrderByCode(productId));
    }

    @Override
    public void deleteByProductId(UUID productId) {
        jpa.deleteByProductId(productId);
    }

    @Override
    public Optional<ProductPresentation> findDefaultByProductId(UUID productId) {
        return jpa.findByProductIdAndIsDefaultTrue(productId).map(mapper::toDomain);
    }
}
