package co.posinvent.domain.repository;

import co.posinvent.domain.model.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ReceiptRepository {

    Receipt save(Receipt receipt);

    Optional<Receipt> findById(UUID id);

    Page<Receipt> findAll(Pageable pageable);

    Page<Receipt> findByWarehouseId(UUID warehouseId, Pageable pageable);

    Page<Receipt> findBySupplierId(UUID supplierId, Pageable pageable);
}
