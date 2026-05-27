package co.posinvent.domain.repository;

import co.posinvent.domain.model.Machinery;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MachineryRepository {
    Machinery save(Machinery m);
    Optional<Machinery> findById(UUID id);
    List<Machinery> findAll();
    void delete(UUID id);
}