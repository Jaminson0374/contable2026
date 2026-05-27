package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.InventoryMovement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KardexMapper {

    @Mapping(target = "movementType", expression = "java(co.posinvent.domain.model.MovementType.valueOf(e.getMovementType()))")
    InventoryMovement toDomain(InventoryMovementEntity e);

    @Mapping(target = "movementType", expression = "java(m.movementType().name())")
    InventoryMovementEntity toEntity(InventoryMovement m);
}
