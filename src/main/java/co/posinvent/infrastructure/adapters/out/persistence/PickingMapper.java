package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Picking;
import co.posinvent.domain.model.Picking.PickingItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
interface PickingMapper {

    @Mapping(target = "items", expression = "java(mapItems(e.getItems()))")
    Picking toDomain(PickingEntity e);

    @Mapping(target = "items", ignore = true)
    PickingEntity toEntity(Picking d);

    @Mapping(target = "picking", ignore = true)
    PickingItemEntity toItemEntity(PickingItem item);

    @Mapping(target = "pickingId", expression = "java(e.getPicking().getId())")
    PickingItem toItemDomain(PickingItemEntity e);

    default List<PickingItem> mapItems(List<PickingItemEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(this::toItemDomain).toList();
    }
}
