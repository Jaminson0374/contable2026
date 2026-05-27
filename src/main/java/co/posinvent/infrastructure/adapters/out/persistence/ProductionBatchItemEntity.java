package co.posinvent.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "production_batch_items")
@Getter
@Setter
public class ProductionBatchItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private ProductionBatchEntity batch;

    @Column(name = "component_product_id", nullable = false)
    private UUID componentProductId;

    @Column(name = "planned_quantity", nullable = false, precision = 15, scale = 4)
    private BigDecimal plannedQuantity;

    @Column(name = "actual_quantity", nullable = false, precision = 15, scale = 4)
    private BigDecimal actualQuantity;

    @Column(name = "unit_cost", nullable = false, precision = 15, scale = 4)
    private BigDecimal unitCost;

    @Column(name = "total_cost", nullable = false, precision = 15, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "kardex_movement_id")
    private UUID kardexMovementId;
}
