package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.ProductionOrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "production_orders")
@Getter @Setter
public class ProductionOrderEntity {
    @Id
    private UUID id;
    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;
    @Column(name = "formula_id", nullable = false)
    private UUID formulaId;
    @Column(name = "planned_quantity", nullable = false)
    private BigDecimal plannedQuantity;
    @Column(name = "planned_date", nullable = false)
    private LocalDate plannedDate;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductionOrderStatus status;
    @Column(name = "warehouse_id", nullable = false)
    private UUID warehouseId;
    @Column(name = "machinery_id")
    private UUID machineryId;
    @Column(columnDefinition = "TEXT")
    private String notes;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "approved_by")
    private String approvedBy;
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name = "approved_at")
    private OffsetDateTime approvedAt;

    @PrePersist
    void prePersist() {
        if (id == null) id = UUID.randomUUID();
        if (createdAt == null) createdAt = OffsetDateTime.now();
        if (status == null) status = ProductionOrderStatus.PLANNED;
    }
}
