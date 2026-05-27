package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Receipt;
import co.posinvent.domain.repository.ReceiptRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ReceiptRepositoryAdapter implements ReceiptRepository {

    private final ReceiptJpaRepository jpa;
    private final ReceiptMapper mapper;

    public ReceiptRepositoryAdapter(ReceiptJpaRepository jpa, ReceiptMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Receipt save(Receipt receipt) {
        var entity = mapper.toEntity(receipt);
        if (receipt.items() != null) {
            var itemEntities = new java.util.ArrayList<ReceiptItemEntity>();
            for (var item : receipt.items()) {
                var ie = mapper.toItemEntity(item);
                ie.setReceipt(entity);
                itemEntities.add(ie);
            }
            entity.getItems().clear();
            entity.getItems().addAll(itemEntities);
        }
        return mapper.toDomain(jpa.save(entity));
    }

    @Override
    public Optional<Receipt> findById(UUID id) {
        return jpa.findByIdWithItems(id).map(mapper::toDomain);
    }

    @Override
    public Page<Receipt> findAll(Pageable pageable) {
        return jpa.findAll(pageable).map(mapper::toDomain);
    }

    @Override
    public Page<Receipt> findByWarehouseId(UUID warehouseId, Pageable pageable) {
        return jpa.findByWarehouseId(warehouseId, pageable).map(mapper::toDomain);
    }

    @Override
    public Page<Receipt> findBySupplierId(UUID supplierId, Pageable pageable) {
        return jpa.findBySupplierId(supplierId, pageable).map(mapper::toDomain);
    }
}
