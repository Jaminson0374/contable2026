package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ThirdPartyCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ThirdPartyCategoryMapper {

    ThirdPartyCategory toDomain(ThirdPartyCategoryEntity entity);

    @Mapping(target = "id", ignore = true)
    ThirdPartyCategoryEntity toEntity(ThirdPartyCategory domain);
}
