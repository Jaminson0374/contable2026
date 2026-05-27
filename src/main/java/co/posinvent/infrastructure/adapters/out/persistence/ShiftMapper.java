package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Shift;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface ShiftMapper {

    Shift toDomain(ShiftEntity entity);

    ShiftEntity toEntity(Shift domain);
}
