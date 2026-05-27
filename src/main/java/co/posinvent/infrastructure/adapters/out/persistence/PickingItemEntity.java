package co.posinvent.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "logistics_picking_items")
@Getter
@Setter
class PickingItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picking_id", nullable = false)
    private PickingEntity picking;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "warehouse_id", nullable = false)
    private UUID warehouseId;

    @Column(name = "location_id")
    private UUID locationId;

    @Column(name = "batch_id")
    private UUID batchId;

    @Column(name = "requested_quantity", nullable = false, precision = 15, scale = 4)
    private BigDecimal requestedQuantity;

    @Column(name = "picked_quantity", nullable = false, precision = 15, scale = 4)
    private BigDecimal pickedQuantity;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
