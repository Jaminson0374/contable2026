package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record CustomPrice(
        UUID id,
        UUID clientId,
        UUID productId,
        BigDecimal price,
        String taxType,
        BigDecimal taxRate
) {}
