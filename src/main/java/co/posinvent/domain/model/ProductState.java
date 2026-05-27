package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductState(
    UUID id,
    String code,
    String name,
    boolean active,
    OffsetDateTime createdAt
) {}
