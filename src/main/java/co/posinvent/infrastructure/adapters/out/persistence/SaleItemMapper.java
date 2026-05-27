package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.SaleItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface SaleItemMapper {

    @Mapping(target = "documentId", source = "document.id")
    SaleItem toDomain(SaleItemEntity entity);

    @Mapping(target = "document", ignore = true)
    SaleItemEntity toEntity(SaleItem domain);
}
