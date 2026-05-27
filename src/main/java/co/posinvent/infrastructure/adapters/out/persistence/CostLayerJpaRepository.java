package co.posinvent.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CostLayerJpaRepository extends JpaRepository<CostLayerEntity, UUID> {

    List<CostLayerEntity> findByProductIdAndBatchIdAndWarehouseIdOrderByEntryDateAsc(
            UUID productId, UUID batchId, UUID warehouseId);

    @Modifying
    @Query("DELETE FROM CostLayerEntity c WHERE c.productId = :productId AND c.batchId = :batchId AND c.warehouseId = :warehouseId")
    void deleteAllByProductBatchWarehouse(
            @Param("productId") UUID productId,
            @Param("batchId") UUID batchId,
            @Param("warehouseId") UUID warehouseId);
}
