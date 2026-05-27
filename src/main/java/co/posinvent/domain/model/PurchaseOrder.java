package co.posinvent.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record PurchaseOrder(
        UUID id,
        UUID supplierId,
        PurchaseOrderStatus status,
        LocalDate orderDate,
        String documentNumber,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version,
        List<PurchaseLineItem> lines
) {
    public PurchaseOrder {
        if (lines == null) lines = List.of();
    }

    public boolean isMutable() {
        return status == PurchaseOrderStatus.PENDING;
    }
}
