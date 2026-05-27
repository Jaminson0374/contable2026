package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductionOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ProductionOrderMapper {
    ProductionOrder toDomain(ProductionOrderEntity entity);
    ProductionOrderEntity toEntity(ProductionOrder domain);
}