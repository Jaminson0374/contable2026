package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CustomerStatementResponse(
        String clientId,
        String clientName,
        LocalDate fromDate,
        LocalDate toDate,
        BigDecimal openingBalance,
        List<StatementEntry> entries,
        BigDecimal closingBalance
) {}
