package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.StockAdjustment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockAdjustmentMapper {

    @Mapping(target = "adjustmentType", expression = "java(co.posinvent.domain.model.AdjustmentType.valueOf(e.getAdjustmentType()))")
    StockAdjustment toDomain(StockAdjustmentEntity e);

    @Mapping(target = "adjustmentType", expression = "java(a.adjustmentType().name())")
    StockAdjustmentEntity toEntity(StockAdjustment a);
}
