package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ProductTypeMapper {
    ProductType toDomain(ProductTypeEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    ProductTypeEntity toEntity(ProductType domain);
}
