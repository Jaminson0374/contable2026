package co.posinvent.application.dto;

import co.posinvent.domain.model.ProductPresentation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductPresentationResponse(
        UUID id,
        UUID productId,
        String code,
        String name,
        UUID unitOfMeasureId,
        String unitOfMeasureName,
        BigDecimal conversionFactor,
        BigDecimal salePrice,
        boolean isDefault,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public static ProductPresentationResponse from(ProductPresentation p, String unitOfMeasureName) {
        return new ProductPresentationResponse(
                p.id(),
                p.productId(),
                p.code(),
                p.name(),
                p.unitOfMeasureId(),
                unitOfMeasureName,
                p.conversionFactor(),
                p.salePrice(),
                p.isDefault(),
                p.active(),
                p.createdAt(),
                p.updatedAt()
        );
    }
}
