package co.posinvent.domain.repository;

import co.posinvent.domain.model.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ShipmentRepository {

    Shipment save(Shipment shipment);

    Optional<Shipment> findById(UUID id);

    Page<Shipment> findAll(Pageable pageable);

    Page<Shipment> findByTransportGuideId(UUID transportGuideId, Pageable pageable);
}
