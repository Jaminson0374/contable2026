package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {

    AuditLog toDomain(AuditLogEntity entity);

    @Mapping(target = "id", ignore = true)
    AuditLogEntity toEntity(AuditLog domain);
}
