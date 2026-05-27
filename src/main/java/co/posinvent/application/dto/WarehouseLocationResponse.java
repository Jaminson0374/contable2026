package co.posinvent.application.dto;

import co.posinvent.domain.model.WarehouseLocation;
import java.time.OffsetDateTime;
import java.util.UUID;

public record WarehouseLocationResponse(
    UUID id,
    UUID warehouseId,
    String name,
    String description,
    boolean active,
    OffsetDateTime createdAt
) {
    public static WarehouseLocationResponse from(WarehouseLocation d) {
        return new WarehouseLocationResponse(d.id(), d.warehouseId(), d.name(), d.description(), d.active(), d.createdAt());
    }
}
