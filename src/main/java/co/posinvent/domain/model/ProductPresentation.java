package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductPresentation(
        UUID id,
        UUID productId,
        String code,
        String name,
        UUID unitOfMeasureId,
        BigDecimal conversionFactor,
        BigDecimal salePrice,
        boolean isDefault,
        boolean active,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public ProductPresentation {
        if (conversionFactor != null && conversionFactor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El factor de conversión debe ser mayor a cero.");
        }
    }
}
