package co.posinvent.domain.repository;

import co.posinvent.domain.model.ProductPresentation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductPresentationRepository {
    ProductPresentation save(ProductPresentation presentation);

    Optional<ProductPresentation> findById(UUID id);

    List<ProductPresentation> findByProductId(UUID productId);

    void deleteByProductId(UUID productId);

    Optional<ProductPresentation> findDefaultByProductId(UUID productId);
}
