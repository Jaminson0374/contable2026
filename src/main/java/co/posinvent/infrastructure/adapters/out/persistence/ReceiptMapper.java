package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Receipt;
import co.posinvent.domain.model.Receipt.ReceiptItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
interface ReceiptMapper {

    @Mapping(target = "items", expression = "java(mapItems(e.getItems()))")
    Receipt toDomain(ReceiptEntity e);

    @Mapping(target = "items", ignore = true)
    ReceiptEntity toEntity(Receipt d);

    @Mapping(target = "receipt", ignore = true)
    ReceiptItemEntity toItemEntity(ReceiptItem item);

    @Mapping(target = "receiptId", expression = "java(e.getReceipt().getId())")
    ReceiptItem toItemDomain(ReceiptItemEntity e);

    default List<ReceiptItem> mapItems(List<ReceiptItemEntity> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(this::toItemDomain).toList();
    }
}
