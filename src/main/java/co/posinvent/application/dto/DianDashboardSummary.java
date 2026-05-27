package co.posinvent.application.dto;

public record DianDashboardSummary(
    long todayEmitted,
    long pendingCount,
    long rejectedCount
) {}
