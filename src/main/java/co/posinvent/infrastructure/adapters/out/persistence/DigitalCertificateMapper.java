package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.DigitalCertificate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DigitalCertificateMapper {

    DigitalCertificate toDomain(DigitalCertificateEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    DigitalCertificateEntity toEntity(DigitalCertificate domain);
}
