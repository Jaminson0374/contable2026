package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Slaughter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface SlaughterMapper {

    Slaughter toDomain(SlaughterEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    SlaughterEntity toEntity(Slaughter domain);
}
