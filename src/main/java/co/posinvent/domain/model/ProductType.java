package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductType(
    UUID id,
    String code,
    String name,
    boolean active,
    OffsetDateTime createdAt
) {}
