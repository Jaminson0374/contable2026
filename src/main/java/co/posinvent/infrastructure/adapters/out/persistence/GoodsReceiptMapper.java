package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.GoodsReceipt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface GoodsReceiptMapper {

    @Mapping(target = "batchIds", source = "batchIds")
    GoodsReceipt toDomain(GoodsReceiptEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "batchIds", source = "batchIds")
    GoodsReceiptEntity toEntity(GoodsReceipt domain);
}
