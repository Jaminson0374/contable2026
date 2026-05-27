package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record Shift(
        UUID id,
        UUID cashRegisterId,
        UUID userId,
        OffsetDateTime openingTime,
        OffsetDateTime closingTime,
        BigDecimal openingAmount,
        BigDecimal closingAmount,
        ShiftStatus status,
        String zReportUrl,
        OffsetDateTime createdAt
) {}
