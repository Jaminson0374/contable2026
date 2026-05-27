package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductFormula;
import co.posinvent.domain.repository.ProductFormulaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
class ProductFormulaRepositoryAdapter implements ProductFormulaRepository {

    private final ProductFormulaJpaRepository jpa;
    private final ProductFormulaMapper mapper;

    ProductFormulaRepositoryAdapter(ProductFormulaJpaRepository jpa, ProductFormulaMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public ProductFormula save(ProductFormula formula) {
        return mapper.toDomain(jpa.save(mapper.toEntity(formula)));
    }

    @Override
    public Optional<ProductFormula> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ProductFormula> findByParentProductId(UUID parentProductId) {
        return mapper.toDomainList(jpa.findByParentProductIdOrderBySequenceNumber(parentProductId));
    }

    @Override
    public List<ProductFormula> findAllByComponentProductId(UUID componentProductId) {
        return mapper.toDomainList(jpa.findAllByComponentProductId(componentProductId));
    }

    @Override
    public void delete(UUID id) {
        jpa.deleteById(id);
    }
}
