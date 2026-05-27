package co.posinvent.infrastructure.adapters.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Embeddable
@Getter
@Setter
class InvoicePaymentEmbeddable {

    @Column(name = "invoice_id", nullable = false)
    private UUID invoiceId;

    @Column(name = "applied_amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal appliedAmount;
}
