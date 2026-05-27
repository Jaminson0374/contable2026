package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Brand(
    UUID id,
    String name,
    boolean active,
    OffsetDateTime createdAt
) {}
