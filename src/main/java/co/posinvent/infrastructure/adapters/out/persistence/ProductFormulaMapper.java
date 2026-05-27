package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductFormula;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductFormulaMapper {

    ProductFormula toDomain(ProductFormulaEntity entity);

    List<ProductFormula> toDomainList(List<ProductFormulaEntity> entities);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductFormulaEntity toEntity(ProductFormula domain);
}
