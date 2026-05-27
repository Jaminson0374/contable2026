package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.DianResolution;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DianResolutionMapper {

    DianResolution toDomain(DianResolutionEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    DianResolutionEntity toEntity(DianResolution domain);
}
