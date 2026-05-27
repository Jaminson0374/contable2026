package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record ReceiptApplication(
        UUID id,
        UUID receiptId,
        UUID arId,
        BigDecimal appliedAmount
) {
    public ReceiptApplication {
        if (appliedAmount == null) {
            throw new IllegalArgumentException("appliedAmount must not be null");
        }
    }
}
