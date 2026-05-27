package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Warehouse(
        UUID id,
        String name,
        String location,
        WarehouseType warehouseType,
        boolean active,
        OffsetDateTime createdAt
) {

    public enum WarehouseType { CANAL, CORTES, VISCERAS, EMBUTIDOS, DECOMISOS, GENERAL }
}
