package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.SalesDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface SalesDocumentMapper {

    @Mapping(target = "isCreditSale", source = "creditSale")
    SalesDocument toDomain(SalesDocumentEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "creditSale", source = "isCreditSale")
    SalesDocumentEntity toEntity(SalesDocument domain);
}
