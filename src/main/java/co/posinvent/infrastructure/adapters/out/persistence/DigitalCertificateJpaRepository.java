package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DigitalCertificateJpaRepository extends JpaRepository<DigitalCertificateEntity, UUID> {
}
