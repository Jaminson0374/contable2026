package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ProductionOrderRequest(
    UUID formulaId,
    BigDecimal plannedQuantity,
    LocalDate plannedDate,
    UUID warehouseId,
    UUID machineryId,
    String notes
) {}