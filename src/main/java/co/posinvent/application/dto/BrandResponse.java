package co.posinvent.application.dto;

import co.posinvent.domain.model.Brand;
import java.time.OffsetDateTime;
import java.util.UUID;

public record BrandResponse(
    UUID id,
    String name,
    boolean active,
    OffsetDateTime createdAt
) {
    public static BrandResponse from(Brand d) {
        return new BrandResponse(d.id(), d.name(), d.active(), d.createdAt());
    }
}
