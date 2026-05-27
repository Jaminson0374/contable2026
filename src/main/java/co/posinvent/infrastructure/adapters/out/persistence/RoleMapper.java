package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toDomain(RoleEntity entity);

    RoleEntity toEntity(Role domain);
}
