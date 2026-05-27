package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DashboardSummary(
    BigDecimal todaySales,
    BigDecimal overdueReceivables,
    int lowStockCount,
    BigDecimal currentMonthMargin,
    LocalDateTime lastUpdated
) {}
