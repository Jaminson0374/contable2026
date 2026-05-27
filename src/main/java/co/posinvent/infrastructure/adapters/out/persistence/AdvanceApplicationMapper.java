package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.AdvanceApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdvanceApplicationMapper {

    AdvanceApplication toDomain(AdvanceApplicationEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    AdvanceApplicationEntity toEntity(AdvanceApplication domain);
}
