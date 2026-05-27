package co.posinvent.application.dto;

import co.posinvent.domain.model.ProductionOrder;
import co.posinvent.domain.model.ProductionOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductionOrderResponse(
    UUID id,
    String orderNumber,
    UUID formulaId,
    String formulaName,
    BigDecimal plannedQuantity,
    LocalDate plannedDate,
    ProductionOrderStatus status,
    UUID warehouseId,
    UUID machineryId,
    String notes,
    String createdBy,
    String approvedBy,
    OffsetDateTime createdAt,
    OffsetDateTime approvedAt,
    UUID batchId
) {
    public static ProductionOrderResponse from(ProductionOrder o) {
        return new ProductionOrderResponse(
                o.id(), o.orderNumber(), o.formulaId(), null,
                o.plannedQuantity(), o.plannedDate(), o.status(),
                o.warehouseId(), o.machineryId(), o.notes(),
                o.createdBy(), o.approvedBy(), o.createdAt(), o.approvedAt(),
                null
        );
    }
}