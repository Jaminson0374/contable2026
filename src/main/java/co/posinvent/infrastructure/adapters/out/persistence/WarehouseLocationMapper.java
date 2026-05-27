package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.WarehouseLocation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface WarehouseLocationMapper {
    WarehouseLocation toDomain(WarehouseLocationEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    WarehouseLocationEntity toEntity(WarehouseLocation domain);
}
