package co.posinvent.application.dto;

import co.posinvent.domain.model.Warehouse;
import co.posinvent.domain.model.Warehouse.WarehouseType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record WarehouseResponse(
        UUID id,
        String name,
        String location,
        WarehouseType warehouseType,
        boolean active,
        OffsetDateTime createdAt
) {
    public static WarehouseResponse from(Warehouse w) {
        return new WarehouseResponse(
                w.id(), w.name(), w.location(),
                w.warehouseType(), w.active(), w.createdAt()
        );
    }
}
