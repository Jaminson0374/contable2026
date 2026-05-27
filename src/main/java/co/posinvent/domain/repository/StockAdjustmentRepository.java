package co.posinvent.domain.repository;

import co.posinvent.domain.model.StockAdjustment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface StockAdjustmentRepository {

    StockAdjustment save(StockAdjustment adjustment);

    Page<StockAdjustment> findAll(Pageable pageable);

    Page<StockAdjustment> findByProductId(UUID productId, Pageable pageable);
}
