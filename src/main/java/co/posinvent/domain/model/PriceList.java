package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PriceList(
    UUID id,
    String code,
    String name,
    String description,
    boolean active,
    OffsetDateTime createdAt
) {}
