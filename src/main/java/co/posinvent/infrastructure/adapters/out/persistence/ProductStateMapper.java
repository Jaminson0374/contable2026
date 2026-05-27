package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductState;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface ProductStateMapper {
    ProductState toDomain(ProductStateEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    ProductStateEntity toEntity(ProductState domain);
}
