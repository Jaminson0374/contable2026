package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.Slaughter.SlaughterSourceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "slaughters")
@Getter
@Setter
class SlaughterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "animal_id", nullable = false, unique = true)
    private UUID animalId;

    @Column(name = "carcass_weight", nullable = false, precision = 10, scale = 3)
    private BigDecimal carcassWeight;

    @Column(name = "yield_percentage", nullable = false, precision = 6, scale = 2)
    private BigDecimal yieldPercentage;

    @Column(name = "slaughter_date", nullable = false)
    private LocalDate slaughterDate;

    @Column(name = "invima_plant", nullable = false, length = 100)
    private String invimaPlant;

    @Column(name = "inspector_id", nullable = false)
    private UUID inspectorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 20)
    private SlaughterSourceType sourceType;

    @Column(length = 300)
    private String justification;

    @Column(name = "purchase_cost", nullable = false, precision = 15, scale = 2)
    private BigDecimal purchaseCost;

    @Column(name = "batch_id", nullable = false, unique = true)
    private UUID batchId;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @Version
    private Long version;

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
