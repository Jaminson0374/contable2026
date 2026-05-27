package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.JournalEntry;
import co.posinvent.domain.model.JournalEntryLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
interface JournalEntryMapper {
    @Mapping(target = "lines", source = "lines")
    JournalEntry toDomain(JournalEntryEntity entity);

    @Mapping(target = "lines", source = "lines")
    JournalEntryEntity toEntity(JournalEntry domain);

    JournalEntryLine toLineDomain(JournalEntryLineEntity entity);
    JournalEntryLineEntity toLineEntity(JournalEntryLine domain);
    List<JournalEntryLine> toLineDomainList(List<JournalEntryLineEntity> entities);
}
