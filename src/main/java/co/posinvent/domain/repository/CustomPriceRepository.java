package co.posinvent.domain.repository;

import co.posinvent.domain.model.CustomPrice;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomPriceRepository {

    Optional<CustomPrice> findByClientIdAndProductId(UUID clientId, UUID productId);

    List<CustomPrice> findAll();

    Optional<CustomPrice> findById(UUID id);

    List<CustomPrice> findByClientId(UUID clientId);

    List<CustomPrice> findByProductId(UUID productId);

    CustomPrice save(CustomPrice customPrice);

    void delete(UUID id);

    boolean existsByClientIdAndProductId(UUID clientId, UUID productId);
}
