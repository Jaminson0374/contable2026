package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record AdvanceApplication(
        UUID id,
        UUID advancePaymentId,
        UUID invoiceId,
        BigDecimal appliedAmount,
        LocalDate applicationDate,
        UUID createdBy,
        OffsetDateTime createdAt
) {}
