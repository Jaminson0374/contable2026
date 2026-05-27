package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.usecase.ReportUseCase;
import co.posinvent.domain.model.IncomeStatementRow;
import co.posinvent.domain.model.ProfitabilityRow;
import co.posinvent.domain.model.SalesByPeriodRow;
import co.posinvent.domain.model.SalesByProductRow;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportUseCase reportUseCase;

    public ReportController(ReportUseCase reportUseCase) {
        this.reportUseCase = reportUseCase;
    }

    @GetMapping("/sales-by-product")
    @PreAuthorize("hasAnyRole('ADMIN','CONTADOR')")
    public ResponseEntity<List<SalesByProductRow>> salesByProduct(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) UUID warehouseId
    ) {
        return ResponseEntity.ok(reportUseCase.salesByProduct(from, to, warehouseId));
    }

    @GetMapping("/sales-by-period")
    @PreAuthorize("hasAnyRole('ADMIN','CONTADOR')")
    public ResponseEntity<List<SalesByPeriodRow>> salesByPeriod(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "DAILY") String granularity
    ) {
        return ResponseEntity.ok(reportUseCase.salesByPeriod(from, to, granularity));
    }

    @GetMapping("/profitability")
    @PreAuthorize("hasAnyRole('ADMIN','CONTADOR')")
    public ResponseEntity<List<ProfitabilityRow>> profitability(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(required = false) UUID warehouseId
    ) {
        return ResponseEntity.ok(reportUseCase.profitability(from, to, warehouseId));
    }

    @GetMapping("/income-statement")
    @PreAuthorize("hasAnyRole('ADMIN','CONTADOR')")
    public ResponseEntity<IncomeStatementRow> incomeStatement(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(reportUseCase.incomeStatement(from, to));
    }
}
