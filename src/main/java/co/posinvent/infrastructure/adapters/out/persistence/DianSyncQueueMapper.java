package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.DianSyncQueueItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DianSyncQueueMapper {

    @Mapping(source = "electronicInvoice.id", target = "electronicInvoiceId")
    DianSyncQueueItem toDomain(DianSyncQueueItemEntity entity);

    @Mapping(target = "electronicInvoice", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    DianSyncQueueItemEntity toEntity(DianSyncQueueItem domain);
}
