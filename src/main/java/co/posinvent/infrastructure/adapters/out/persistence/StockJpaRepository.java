package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface StockJpaRepository extends JpaRepository<InventoryStockEntity, UUID> {

    Optional<InventoryStockEntity> findByProductIdAndBatchIdAndWarehouseId(
            UUID productId, UUID batchId, UUID warehouseId);

    List<InventoryStockEntity> findByWarehouseId(UUID warehouseId);

    List<InventoryStockEntity> findByProductId(UUID productId);

    List<InventoryStockEntity> findByBatchId(UUID batchId);
}
