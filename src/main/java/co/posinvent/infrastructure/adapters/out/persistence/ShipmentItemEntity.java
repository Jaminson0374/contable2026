package co.posinvent.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "logistics_shipment_items")
@Getter
@Setter
class ShipmentItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    private ShipmentEntity shipment;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "picking_id")
    private UUID pickingId;

    @Column(name = "batch_id")
    private UUID batchId;

    @Column(name = "quantity", nullable = false, precision = 15, scale = 4)
    private BigDecimal quantity;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
