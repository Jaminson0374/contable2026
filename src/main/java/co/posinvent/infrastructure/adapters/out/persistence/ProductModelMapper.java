package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ProductModelMapper {
    ProductModel toDomain(ProductModelEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    ProductModelEntity toEntity(ProductModel domain);
}
