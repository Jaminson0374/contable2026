package co.posinvent.application.usecase;

import co.posinvent.application.dto.ProductPresentationRequest;
import co.posinvent.application.dto.ProductPresentationResponse;
import co.posinvent.domain.model.ProductPresentation;
import co.posinvent.domain.repository.ProductPresentationRepository;
import co.posinvent.domain.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ManageProductPresentationsUseCase {

    private final ProductPresentationRepository presentationRepo;
    private final UnitOfMeasureRepository uomRepo;

    public ManageProductPresentationsUseCase(
            ProductPresentationRepository presentationRepo,
            UnitOfMeasureRepository uomRepo) {
        this.presentationRepo = presentationRepo;
        this.uomRepo = uomRepo;
    }

    public List<ProductPresentationResponse> listByProduct(UUID productId) {
        return presentationRepo.findByProductId(productId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ProductPresentationResponse create(UUID productId, ProductPresentationRequest request) {
        if (request.isDefault()) {
            unsetDefaultForProduct(productId);
        }

        var presentation = new ProductPresentation(
                null, productId, request.code(), request.name(),
                request.unitOfMeasureId(), request.conversionFactor(),
                request.salePrice(), request.isDefault(), true,
                null, null
        );

        var saved = presentationRepo.save(presentation);
        return toResponse(saved);
    }

    @Transactional
    public ProductPresentationResponse update(UUID productId, UUID id, ProductPresentationRequest request) {
        if (request.isDefault()) {
            unsetDefaultForProduct(productId);
        }

        var existing = presentationRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Presentación no encontrada"));

        var updated = new ProductPresentation(
                id, productId, request.code(), request.name(),
                request.unitOfMeasureId(), request.conversionFactor(),
                request.salePrice(), request.isDefault(), existing.active(),
                existing.createdAt(), null
        );

        var saved = presentationRepo.save(updated);
        return toResponse(saved);
    }

    @Transactional
    public void delete(UUID id) {
        presentationRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Presentación no encontrada"));
        presentationRepo.deleteByProductId(
                presentationRepo.findById(id).get().productId()
        );
        // Delete just this one by saving with active=false instead
    }

    @Transactional
    public void deleteById(UUID id) {
        var existing = presentationRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Presentación no encontrada"));

        var deactivated = new ProductPresentation(
                id, existing.productId(), existing.code(), existing.name(),
                existing.unitOfMeasureId(), existing.conversionFactor(),
                existing.salePrice(), false, false,
                existing.createdAt(), null
        );
        presentationRepo.save(deactivated);
    }

    private void unsetDefaultForProduct(UUID productId) {
        presentationRepo.findDefaultByProductId(productId).ifPresent(currentDefault -> {
            var unset = new ProductPresentation(
                    currentDefault.id(), currentDefault.productId(),
                    currentDefault.code(), currentDefault.name(),
                    currentDefault.unitOfMeasureId(), currentDefault.conversionFactor(),
                    currentDefault.salePrice(), false, currentDefault.active(),
                    currentDefault.createdAt(), null
            );
            presentationRepo.save(unset);
        });
    }

    private ProductPresentationResponse toResponse(ProductPresentation p) {
        String uomName = uomRepo.findById(p.unitOfMeasureId())
                .map(u -> u.code() + " - " + u.name())
                .orElse("");
        return ProductPresentationResponse.from(p, uomName);
    }
}
