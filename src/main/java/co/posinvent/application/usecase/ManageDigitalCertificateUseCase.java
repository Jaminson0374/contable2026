package co.posinvent.application.usecase;

import co.posinvent.application.dto.UploadCertificateRequest;
import co.posinvent.domain.model.DigitalCertificate;
import co.posinvent.domain.repository.DigitalCertificateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class ManageDigitalCertificateUseCase {

    private final DigitalCertificateRepository repository;

    public ManageDigitalCertificateUseCase(DigitalCertificateRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<DigitalCertificate> listAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public DigitalCertificate getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new co.posinvent.domain.exception.ResourceNotFoundException("Certificado", id));
    }

    @Transactional
    public DigitalCertificate upload(byte[] fileBytes, String password, String fileName) {
        var validUntil = extractValidUntil(fileBytes, password);
        var encodedPassword = password != null ? Base64.getEncoder().encodeToString(password.getBytes()) : null;

        var certificate = new DigitalCertificate(
                null,
                fileName != null ? fileName : "certificate",
                fileBytes,
                encodedPassword,
                validUntil,
                true,
                null
        );

        return repository.save(certificate);
    }

    @Transactional
    public void delete(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new co.posinvent.domain.exception.ResourceNotFoundException("Certificado", id);
        }
        repository.deleteById(id);
    }

    private LocalDate extractValidUntil(byte[] fileBytes, String password) {
        try {
            var ks = KeyStore.getInstance("PKCS12");
            var inputStream = new java.io.ByteArrayInputStream(fileBytes);
            ks.load(inputStream, password != null ? password.toCharArray() : new char[0]);

            var aliases = ks.aliases();
            if (aliases.hasMoreElements()) {
                var alias = aliases.nextElement();
                var cert = (X509Certificate) ks.getCertificate(alias);
                if (cert != null) {
                    return Instant.ofEpochMilli(cert.getNotAfter().getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                }
            }
        } catch (Exception e) {
            // If we can't extract the date, default to 1 year from now
        }
        return LocalDate.now().plusYears(1);
    }
}
