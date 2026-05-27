package co.posinvent.application.usecase;

import co.posinvent.application.dto.StockResponse;
import co.posinvent.domain.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StockQueryUseCase {

    private final StockRepository stockRepository;

    public StockQueryUseCase(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional(readOnly = true)
    public List<StockResponse> getByWarehouse(UUID warehouseId) {
        return stockRepository.findByWarehouse(warehouseId).stream()
                .map(StockResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StockResponse> getByProduct(UUID productId) {
        return stockRepository.findByProduct(productId).stream()
                .map(StockResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StockResponse> getByBatch(UUID batchId) {
        return stockRepository.findByBatch(batchId).stream()
                .map(StockResponse::from)
                .toList();
    }
}
