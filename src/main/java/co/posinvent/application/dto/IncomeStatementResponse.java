package co.posinvent.application.dto;

import co.posinvent.domain.model.IncomeStatementRow;
import java.math.BigDecimal;
import java.util.List;

public record IncomeStatementResponse(
        BigDecimal totalRevenue,
        BigDecimal totalCogs,
        BigDecimal grossMargin,
        BigDecimal totalExpenses,
        BigDecimal netIncome,
        String from,
        String to,
        List<RevenueItem> revenueDetails,
        List<RevenueItem> cogsDetails,
        List<RevenueItem> expenseDetails
) {
    public record RevenueItem(
            String label,
            BigDecimal amount
    ) {}

    public static IncomeStatementResponse from(IncomeStatementRow row, String from, String to) {
        return new IncomeStatementResponse(
                row.totalRevenue(), row.totalCogs(), row.grossMargin(),
                row.totalExpenses(), row.netIncome(),
                from, to,
                row.revenueDetails().stream()
                        .map(r -> new RevenueItem(r.label(), r.amount()))
                        .toList(),
                row.cogsDetails().stream()
                        .map(r -> new RevenueItem(r.label(), r.amount()))
                        .toList(),
                row.expenseDetails().stream()
                        .map(r -> new RevenueItem(r.label(), r.amount()))
                        .toList()
        );
    }
}
