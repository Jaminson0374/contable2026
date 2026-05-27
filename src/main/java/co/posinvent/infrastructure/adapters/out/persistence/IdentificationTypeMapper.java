package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.IdentificationType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface IdentificationTypeMapper {

    IdentificationType toDomain(IdentificationTypeEntity entity);

    @Mapping(target = "id", ignore = true)
    IdentificationTypeEntity toEntity(IdentificationType domain);
}
