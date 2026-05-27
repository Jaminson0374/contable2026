package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Batch.BatchStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "batches")
@Getter
@Setter
public class BatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "supplier_id", nullable = false)
    private UUID supplierId;

    @Column(name = "warehouse_id", nullable = false)
    private UUID warehouseId;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "initial_weight", nullable = false, precision = 10, scale = 3)
    private BigDecimal initialWeight;

    @Column(name = "purchase_cost", nullable = false, precision = 15, scale = 2)
    private BigDecimal purchaseCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BatchStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Column(name = "source_receipt_id")
    private UUID sourceReceiptId;

    @Column(name = "oc_id")
    private UUID ocId;

    @PrePersist
    void prePersist() {
        createdAt = OffsetDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
