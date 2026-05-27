package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.DebitCreditNote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface DebitCreditNoteMapper {

    DebitCreditNote toDomain(DebitCreditNoteEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DebitCreditNoteEntity toEntity(DebitCreditNote domain);
}
