package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.InvoiceStatus;
import co.posinvent.domain.model.SupplierInvoice;
import co.posinvent.domain.repository.SupplierInvoiceRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface SupplierInvoiceMapper {

    SupplierInvoice toDomain(SupplierInvoiceEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SupplierInvoiceEntity toEntity(SupplierInvoice domain);
}
