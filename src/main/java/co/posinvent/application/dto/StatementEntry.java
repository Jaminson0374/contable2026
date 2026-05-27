package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record StatementEntry(
        LocalDate date,
        String documentNumber,
        String type,
        String description,
        BigDecimal debit,
        BigDecimal credit,
        BigDecimal balance
) {
    public StatementEntry {
        if (debit == null) debit = BigDecimal.ZERO;
        if (credit == null) credit = BigDecimal.ZERO;
        if (balance == null) balance = BigDecimal.ZERO;
    }
}
