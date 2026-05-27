package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ProductCategoryMapper {
    ProductCategory toDomain(ProductCategoryEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    ProductCategoryEntity toEntity(ProductCategory domain);
}
