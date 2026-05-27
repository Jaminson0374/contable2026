package co.posinvent.application.dto;

import co.posinvent.domain.model.ProductCategory;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductCategoryResponse(
    UUID id,
    String name,
    boolean active,
    OffsetDateTime createdAt
) {
    public static ProductCategoryResponse from(ProductCategory d) {
        return new ProductCategoryResponse(d.id(), d.name(), d.active(), d.createdAt());
    }
}
