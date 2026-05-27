package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.StockDisposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockDisposalMapper {
    @Mapping(target = "disposalType", expression = "java(co.posinvent.domain.model.DisposalType.valueOf(e.getDisposalType()))")
    StockDisposal toDomain(StockDisposalEntity e);
    @Mapping(target = "disposalType", expression = "java(d.disposalType().name())")
    StockDisposalEntity toEntity(StockDisposal d);
}
