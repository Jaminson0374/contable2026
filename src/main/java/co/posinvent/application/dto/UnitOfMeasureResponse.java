package co.posinvent.application.dto;

import co.posinvent.domain.model.UnitOfMeasure;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UnitOfMeasureResponse(
    UUID id,
    String code,
    String name,
    String baseUnit,
    boolean active,
    OffsetDateTime createdAt
) {
    public static UnitOfMeasureResponse from(UnitOfMeasure d) {
        return new UnitOfMeasureResponse(d.id(), d.code(), d.name(), d.baseUnit(), d.active(), d.createdAt());
    }
}
