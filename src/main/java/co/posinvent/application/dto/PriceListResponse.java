package co.posinvent.application.dto;

import co.posinvent.domain.model.PriceList;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PriceListResponse(
    UUID id,
    String code,
    String name,
    String description,
    boolean active,
    OffsetDateTime createdAt
) {
    public static PriceListResponse from(PriceList d) {
        return new PriceListResponse(d.id(), d.code(), d.name(), d.description(), d.active(), d.createdAt());
    }
}
