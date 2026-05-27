package co.posinvent.domain.repository;

import co.posinvent.domain.model.DigitalCertificate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DigitalCertificateRepository {
    DigitalCertificate save(DigitalCertificate certificate);
    Optional<DigitalCertificate> findById(UUID id);
    List<DigitalCertificate> findAll();
    void deleteById(UUID id);
}
