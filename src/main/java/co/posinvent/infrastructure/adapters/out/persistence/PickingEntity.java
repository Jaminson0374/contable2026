package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.PickingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "logistics_pickings")
@Getter
@Setter
class PickingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "picking_number", nullable = false, unique = true, length = 30)
    private String pickingNumber;

    @Column(name = "picking_date", nullable = false)
    private LocalDate pickingDate;

    @Column(name = "warehouse_id", nullable = false)
    private UUID warehouseId;

    @Column(name = "shipment_id")
    private UUID shipmentId;

    @Column(name = "sales_order_id")
    private UUID salesOrderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PickingStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

    @OneToMany(mappedBy = "picking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PickingItemEntity> items = new ArrayList<>();

    @PrePersist
    void prePersist() {
        createdAt = OffsetDateTime.now();
        updatedAt = createdAt;
        if (status == null) status = PickingStatus.PLANNED;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
