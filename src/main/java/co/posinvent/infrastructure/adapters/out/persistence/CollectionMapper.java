package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Collection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface CollectionMapper {

    Collection toDomain(CollectionEntity entity);

    CollectionEntity toEntity(Collection domain);
}
