package co.posinvent.domain.repository;

import co.posinvent.domain.model.IncomeStatementRow;
import co.posinvent.domain.model.ProfitabilityRow;
import co.posinvent.domain.model.SalesByPeriodRow;
import co.posinvent.domain.model.SalesByProductRow;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReportRepository {

    List<SalesByProductRow> salesByProduct(LocalDateTime from, LocalDateTime to, UUID warehouseId);

    List<SalesByPeriodRow> salesByPeriod(LocalDateTime from, LocalDateTime to, String granularity);

    List<ProfitabilityRow> profitability(LocalDateTime from, LocalDateTime to, UUID warehouseId);

    IncomeStatementRow incomeStatement(LocalDateTime from, LocalDateTime to);
}
