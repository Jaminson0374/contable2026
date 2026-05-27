package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record SupplierInvoice(
        UUID id,
        UUID supplierId,
        String invoiceNumber,
        LocalDate issueDate,
        LocalDate dueDate,
        BigDecimal subtotal,
        BigDecimal ivaTotal,
        BigDecimal retentionTotal,
        BigDecimal total,
        InvoiceStatus status,
        String notes,
        UUID createdBy,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt,
        Long version,
        List<UUID> ocIds
) {
    public SupplierInvoice {
        if (ocIds == null) ocIds = List.of();
    }

    /**
     * Whether this invoice can transition to RECONCILED.
     */
    public boolean isReconcilable() {
        return status == InvoiceStatus.PENDING;
    }

    /**
     * Whether this invoice can transition to DISPUTED.
     */
    public boolean isDisputable() {
        return status == InvoiceStatus.PENDING || status == InvoiceStatus.RECONCILED;
    }
}
