package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.PurchaseOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface PurchaseOrderMapper {

    PurchaseOrder toDomain(PurchaseOrderEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    PurchaseOrderEntity toEntity(PurchaseOrder domain);
}
