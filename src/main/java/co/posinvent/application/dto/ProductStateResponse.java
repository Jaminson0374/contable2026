package co.posinvent.application.dto;

import co.posinvent.domain.model.ProductState;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductStateResponse(
    UUID id,
    String code,
    String name,
    boolean active,
    OffsetDateTime createdAt
) {
    public static ProductStateResponse from(ProductState d) {
        return new ProductStateResponse(d.id(), d.code(), d.name(), d.active(), d.createdAt());
    }
}
