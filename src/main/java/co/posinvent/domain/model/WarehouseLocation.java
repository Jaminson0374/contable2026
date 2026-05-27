package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record WarehouseLocation(
    UUID id,
    UUID warehouseId,
    String name,
    String description,
    boolean active,
    OffsetDateTime createdAt
) {}
