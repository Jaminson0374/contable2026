package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.PriceList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface PriceListMapper {
    PriceList toDomain(PriceListEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    PriceListEntity toEntity(PriceList domain);
}
