package co.posinvent.application.dto;

import co.posinvent.domain.model.ProductType;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductTypeResponse(
    UUID id,
    String code,
    String name,
    boolean active,
    OffsetDateTime createdAt
) {
    public static ProductTypeResponse from(ProductType d) {
        return new ProductTypeResponse(d.id(), d.code(), d.name(), d.active(), d.createdAt());
    }
}
