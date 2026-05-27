package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Department;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface DepartmentMapper {

    Department toDomain(DepartmentEntity entity);
}
