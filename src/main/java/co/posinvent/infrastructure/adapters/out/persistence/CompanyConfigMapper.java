package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.CompanyConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyConfigMapper {

    @Mapping(source = "mainWarehouse.id", target = "mainWarehouseId")
    CompanyConfig toDomain(CompanyConfigEntity entity);

    @Mapping(target = "mainWarehouse", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CompanyConfigEntity toEntity(CompanyConfig domain);
}
