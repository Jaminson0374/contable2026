package co.posinvent.infrastructure.adapters.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "product_price_entries")
@Getter
@Setter
class ProductPriceEntryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(name = "price_list_id", nullable = false)
    private UUID priceListId;

    @Column(nullable = false, precision = 15, scale = 4)
    private BigDecimal price;

    @Column(name = "profit_margin", nullable = false, precision = 5, scale = 2)
    private BigDecimal profitMargin;

    @Column(name = "last_updated")
    private OffsetDateTime lastUpdated;

    @PrePersist
    void prePersist() {
        if (lastUpdated == null) lastUpdated = OffsetDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }
}
