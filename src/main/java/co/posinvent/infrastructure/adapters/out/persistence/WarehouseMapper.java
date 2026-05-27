package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Warehouse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface WarehouseMapper {

    Warehouse toDomain(WarehouseEntity entity);
}
