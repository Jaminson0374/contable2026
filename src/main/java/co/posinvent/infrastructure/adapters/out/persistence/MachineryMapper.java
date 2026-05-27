package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Machinery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface MachineryMapper {
    Machinery toDomain(MachineryEntity e);
    MachineryEntity toEntity(Machinery d);
}