package co.posinvent.domain.repository;

import co.posinvent.domain.model.UnitOfMeasure;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UnitOfMeasureRepository {
    List<UnitOfMeasure> findAllActive();
    Optional<UnitOfMeasure> findById(UUID id);
    UnitOfMeasure save(UnitOfMeasure entity);
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
