package co.posinvent.domain.repository;

import co.posinvent.domain.model.IncomeStatementRow;
import co.posinvent.domain.model.ProfitabilityRow;
import co.posinvent.domain.model.SalesByPeriodRow;
import co.posinvent.domain.model.SalesByProductRow;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReportRepository {

    List<SalesByProductRow> salesByProduct(LocalDate from, LocalDate to, UUID warehouseId);

    List<SalesByPeriodRow> salesByPeriod(LocalDate from, LocalDate to, String granularity);

    List<ProfitabilityRow> profitability(LocalDate from, LocalDate to, UUID warehouseId);

    IncomeStatementRow incomeStatement(LocalDate from, LocalDate to);
}
