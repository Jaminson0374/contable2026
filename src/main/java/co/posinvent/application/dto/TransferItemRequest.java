package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferItemRequest(
        UUID productId,
        UUID batchId,
        BigDecimal quantity
) {}
