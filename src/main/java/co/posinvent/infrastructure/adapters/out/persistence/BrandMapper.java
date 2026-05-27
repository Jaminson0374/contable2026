package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface BrandMapper {
    Brand toDomain(BrandEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    BrandEntity toEntity(Brand domain);
}
