package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.CostLayer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CostLayerMapper {

    CostLayer toDomain(CostLayerEntity e);

    CostLayerEntity toEntity(CostLayer layer);
}
