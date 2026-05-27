package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.CustomPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface CustomPriceMapper {
    CustomPrice toDomain(CustomPriceEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    CustomPriceEntity toEntity(CustomPrice domain);
}
