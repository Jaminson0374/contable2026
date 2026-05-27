package co.posinvent.infrastructure.adapters.out.persistence;

import co.posinvent.domain.model.TransportGuideStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "logistics_transport_guides")
@Getter
@Setter
class TransportGuideEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "guide_number", nullable = false, unique = true, length = 30)
    private String guideNumber;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "vehicle_plate", length = 20)
    private String vehiclePlate;

    @Column(name = "driver_name", length = 150)
    private String driverName;

    @Column(name = "driver_id", length = 20)
    private String driverId;

    @Column(name = "origin_address", length = 300)
    private String originAddress;

    @Column(name = "destination_address", length = 300)
    private String destinationAddress;

    @Column(name = "carrier_name", length = 150)
    private String carrierName;

    @Column(name = "estimated_delivery")
    private LocalDate estimatedDelivery;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransportGuideStatus status;

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

    @PrePersist
    void prePersist() {
        createdAt = OffsetDateTime.now();
        updatedAt = createdAt;
        if (status == null) status = TransportGuideStatus.CREATED;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
