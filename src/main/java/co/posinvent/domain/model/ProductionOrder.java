package co.posinvent.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProductionOrder(
    UUID id,
    String orderNumber,
    UUID formulaId,
    java.math.BigDecimal plannedQuantity,
    LocalDate plannedDate,
    ProductionOrderStatus status,
    UUID warehouseId,
    UUID machineryId,
    String notes,
    String createdBy,
    String approvedBy,
    OffsetDateTime createdAt,
    OffsetDateTime approvedAt
) {}