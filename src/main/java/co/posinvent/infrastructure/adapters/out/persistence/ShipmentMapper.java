package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Shipment;
import co.posinvent.domain.model.Shipment.ShipmentItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
interface ShipmentMapper {

    @Mapping(target = "items", expression = "java(mapItems(e.getItems()))")
    Shipment toDomain(ShipmentEntity e);

    @Mapping(target = "items", ignore = true)
    ShipmentEntity toEntity(Shipment d);

    @Mapping(target = "shipment", ignore = true)
    ShipmentItemEntity toItemEntity(ShipmentItem item);

    @Mapping(target = "shipmentId", expression = "java(e.getShipment().getId())")
    ShipmentItem toItemDomain(ShipmentItemEntity e);

    default List<ShipmentItem> mapItems(List<ShipmentItemEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(this::toItemDomain).toList();
    }
}
