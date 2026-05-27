package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Shipment;
import co.posinvent.domain.repository.ShipmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ShipmentRepositoryAdapter implements ShipmentRepository {

    private final ShipmentJpaRepository jpa;
    private final ShipmentMapper mapper;

    public ShipmentRepositoryAdapter(ShipmentJpaRepository jpa, ShipmentMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Shipment save(Shipment shipment) {
        var entity = mapper.toEntity(shipment);
        if (shipment.items() != null) {
            var itemEntities = new java.util.ArrayList<ShipmentItemEntity>();
            for (var item : shipment.items()) {
                var ie = mapper.toItemEntity(item);
                ie.setShipment(entity);
                itemEntities.add(ie);
            }
            entity.getItems().clear();
            entity.getItems().addAll(itemEntities);
        }
        return mapper.toDomain(jpa.save(entity));
    }

    @Override
    public Optional<Shipment> findById(UUID id) {
        return jpa.findByIdWithItems(id).map(mapper::toDomain);
    }

    @Override
    public Page<Shipment> findAll(Pageable pageable) {
        return jpa.findAll(pageable).map(mapper::toDomain);
    }

    @Override
    public Page<Shipment> findByTransportGuideId(UUID transportGuideId, Pageable pageable) {
        return jpa.findByTransportGuideId(transportGuideId, pageable).map(mapper::toDomain);
    }
}
