package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductPresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductPresentationMapper {

    @Mapping(target = "isDefault", source = "default")
    ProductPresentation toDomain(ProductPresentationEntity entity);

    List<ProductPresentation> toDomainList(List<ProductPresentationEntity> entities);

    @Mapping(target = "default", source = "isDefault")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductPresentationEntity toEntity(ProductPresentation domain);
}
