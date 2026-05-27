package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductionBatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductionBatchMapper {

    @Mapping(target = "formulaId", source = "formulaId")
    ProductionBatch toDomain(ProductionBatchEntity entity);

    List<ProductionBatch> toDomainList(List<ProductionBatchEntity> entities);

    @Mapping(target = "batchItems", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProductionBatchEntity toEntity(ProductionBatch domain);
}
