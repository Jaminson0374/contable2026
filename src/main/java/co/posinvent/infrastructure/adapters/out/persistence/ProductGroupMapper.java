package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ProductGroupMapper {
    ProductGroup toDomain(ProductGroupEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    ProductGroupEntity toEntity(ProductGroup domain);
}
