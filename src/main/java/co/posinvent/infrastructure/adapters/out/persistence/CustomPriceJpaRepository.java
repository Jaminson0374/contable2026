package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomPriceJpaRepository extends JpaRepository<CustomPriceEntity, UUID> {
    Optional<CustomPriceEntity> findByClientIdAndProductId(UUID clientId, UUID productId);

    List<CustomPriceEntity> findByClientId(UUID clientId);

    List<CustomPriceEntity> findByProductId(UUID productId);

    boolean existsByClientIdAndProductId(UUID clientId, UUID productId);
}
