package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record IncomeStatementRow(
        BigDecimal totalRevenue,
        BigDecimal totalCogs,
        BigDecimal grossMargin,
        BigDecimal totalExpenses,
        BigDecimal netIncome,
        List<RevenueItem> revenueDetails,
        List<RevenueItem> cogsDetails,
        List<RevenueItem> expenseDetails
) {
    public IncomeStatementRow {
        if (revenueDetails == null) revenueDetails = List.of();
        if (cogsDetails == null) cogsDetails = List.of();
        if (expenseDetails == null) expenseDetails = List.of();
    }

    public record RevenueItem(
            String label,
            BigDecimal amount
    ) {}
}
