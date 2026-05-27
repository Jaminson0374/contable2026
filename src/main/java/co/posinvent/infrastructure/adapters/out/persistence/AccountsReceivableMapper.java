package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.AccountsReceivable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountsReceivableMapper {

    @Mapping(target = "status", expression = "java(co.posinvent.domain.model.AccountsReceivable.ArStatus.valueOf(e.getStatus()))")
    AccountsReceivable toDomain(AccountsReceivableEntity e);

    @Mapping(target = "status", expression = "java(a.status().name())")
    AccountsReceivableEntity toEntity(AccountsReceivable a);
}
