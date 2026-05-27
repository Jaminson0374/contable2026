package co.posinvent.application.usecase;

import co.posinvent.domain.model.IncomeStatementRow;
import co.posinvent.domain.model.ProfitabilityRow;
import co.posinvent.domain.model.SalesByPeriodRow;
import co.posinvent.domain.model.SalesByProductRow;
import co.posinvent.domain.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ReportUseCase {

    private final ReportRepository reportRepo;

    public ReportUseCase(ReportRepository reportRepo) {
        this.reportRepo = reportRepo;
    }

    public List<SalesByProductRow> salesByProduct(LocalDate from, LocalDate to, UUID warehouseId) {
        if (from != null && to != null && !from.isBefore(to)) {
            throw new IllegalArgumentException("from debe ser anterior a to");
        }
        return reportRepo.salesByProduct(from, to, warehouseId);
    }

    public List<SalesByPeriodRow> salesByPeriod(LocalDate from, LocalDate to, String granularity) {
        if (from != null && to != null && !from.isBefore(to)) {
            throw new IllegalArgumentException("from debe ser anterior a to");
        }
        if (granularity != null && !granularity.matches("DAILY|WEEKLY|MONTHLY")) {
            throw new IllegalArgumentException("granularity debe ser DAILY, WEEKLY, o MONTHLY");
        }
        return reportRepo.salesByPeriod(from, to, granularity);
    }

    public List<ProfitabilityRow> profitability(LocalDate from, LocalDate to, UUID warehouseId) {
        return reportRepo.profitability(from, to, warehouseId);
    }

    public IncomeStatementRow incomeStatement(LocalDate from, LocalDate to) {
        return reportRepo.incomeStatement(from, to);
    }
}
