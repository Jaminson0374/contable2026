package co.posinvent.application.dto;

import co.posinvent.domain.model.ProductModel;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductModelResponse(
    UUID id,
    String name,
    UUID brandId,
    boolean active,
    OffsetDateTime createdAt
) {
    public static ProductModelResponse from(ProductModel d) {
        return new ProductModelResponse(d.id(), d.name(), d.brandId(), d.active(), d.createdAt());
    }
}
