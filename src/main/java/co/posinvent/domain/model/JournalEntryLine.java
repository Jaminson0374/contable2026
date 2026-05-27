package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record JournalEntryLine(
    UUID id,
    UUID entryId,
    UUID accountId,
    BigDecimal debit,
    BigDecimal credit,
    String description
) {}