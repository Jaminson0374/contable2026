package co.posinvent.domain.repository;

import co.posinvent.domain.model.ProductFormula;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductFormulaRepository {
    ProductFormula save(ProductFormula formula);
    Optional<ProductFormula> findById(UUID id);
    List<ProductFormula> findByParentProductId(UUID parentProductId);
    List<ProductFormula> findAllByComponentProductId(UUID componentProductId);
    void delete(UUID id);
}
