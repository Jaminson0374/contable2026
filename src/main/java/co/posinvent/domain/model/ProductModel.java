package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductModel(
    UUID id,
    String name,
    UUID brandId,
    boolean active,
    OffsetDateTime createdAt
) {}
