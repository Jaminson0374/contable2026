package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record StockDisposal(
        UUID id,
        UUID productId,
        UUID batchId,
        UUID warehouseId,
        DisposalType disposalType,
        BigDecimal quantity,
        BigDecimal unitCost,
        String reason,
        String createdBy,
        OffsetDateTime createdAt
) {}
