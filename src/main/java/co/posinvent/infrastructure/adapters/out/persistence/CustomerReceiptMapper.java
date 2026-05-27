package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.CustomerReceipt;
import co.posinvent.domain.model.ReceiptApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface CustomerReceiptMapper {

    @Mapping(target = "method", expression = "java(co.posinvent.domain.model.CustomerReceipt.PaymentMethod.valueOf(e.getMethod()))")
    @Mapping(target = "applications", source = "applications")
    CustomerReceipt toDomain(CustomerReceiptEntity e);

    @Mapping(target = "method", expression = "java(r.method().name())")
    @Mapping(target = "createdAt", ignore = true)
    CustomerReceiptEntity toEntity(CustomerReceipt r);

    ReceiptApplication toDomain(ReceiptApplicationEntity e);

    @Mapping(target = "receipt", ignore = true)
    ReceiptApplicationEntity toEntity(ReceiptApplication a);
}
