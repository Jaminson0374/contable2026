package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.InventoryStock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface StockMapper {

    InventoryStock toDomain(InventoryStockEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    InventoryStockEntity toEntity(InventoryStock domain);
}
