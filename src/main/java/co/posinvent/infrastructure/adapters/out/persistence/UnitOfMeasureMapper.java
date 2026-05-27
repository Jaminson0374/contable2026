package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.UnitOfMeasure;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface UnitOfMeasureMapper {
    UnitOfMeasure toDomain(UnitOfMeasureEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    UnitOfMeasureEntity toEntity(UnitOfMeasure domain);
}
