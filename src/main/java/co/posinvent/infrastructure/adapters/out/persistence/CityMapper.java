package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface CityMapper {

    City toDomain(CityEntity entity);
}
