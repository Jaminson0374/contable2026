package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.TransportGuide;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface TransportGuideMapper {

    TransportGuide toDomain(TransportGuideEntity e);

    TransportGuideEntity toEntity(TransportGuide d);
}
