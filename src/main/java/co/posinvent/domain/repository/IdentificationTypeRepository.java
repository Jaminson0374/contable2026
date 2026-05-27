package co.posinvent.domain.repository;

import co.posinvent.domain.model.IdentificationType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IdentificationTypeRepository {

    List<IdentificationType> findAllActive();

    Optional<IdentificationType> findById(UUID id);

    IdentificationType save(IdentificationType type);
}
