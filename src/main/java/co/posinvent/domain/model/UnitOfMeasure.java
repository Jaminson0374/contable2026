package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UnitOfMeasure(
    UUID id,
    String code,
    String name,
    String baseUnit,
    boolean active,
    OffsetDateTime createdAt
) {}
