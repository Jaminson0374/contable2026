package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.usecase.CreateJournalEntryUseCase;
import co.posinvent.domain.model.JournalEntryLine;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api/v1/journal-entries")
public class JournalEntryController {
    private final CreateJournalEntryUseCase useCase;
    public JournalEntryController(CreateJournalEntryUseCase u) { this.useCase = u; }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','CONTADOR')")
    public ResponseEntity<?> createManual(@RequestBody Map<String, Object> body) {
        var entryDate = LocalDate.parse((String) body.get("entryDate"));
        var description = (String) body.getOrDefault("description", "");

        @SuppressWarnings("unchecked")
        var linesRaw = (List<Map<String, Object>>) body.get("lines");
        var lines = new ArrayList<JournalEntryLine>();
        for (var l : linesRaw) {
            var accountId = UUID.fromString((String) l.get("accountId"));
            var debit = new BigDecimal(l.getOrDefault("debit", 0).toString());
            var credit = new BigDecimal(l.getOrDefault("credit", 0).toString());
            lines.add(new JournalEntryLine(null, null, accountId, debit, credit, (String) l.get("description")));
        }
        return ResponseEntity.ok(useCase.createManual(entryDate, description, lines));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','CONTADOR')")
    public ResponseEntity<?> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sourceType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(useCase.list(sourceType, from, to, page, size));
    }

    @GetMapping("/ledger")
    @PreAuthorize("hasAnyRole('ADMIN','CONTADOR')")
    public ResponseEntity<?> ledger(
            @RequestParam UUID accountId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(useCase.ledger(accountId, from, to));
    }

    @GetMapping("/trial-balance")
    @PreAuthorize("hasAnyRole('ADMIN','CONTADOR')")
    public ResponseEntity<?> trialBalance(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(useCase.trialBalance(from, to));
    }
}
