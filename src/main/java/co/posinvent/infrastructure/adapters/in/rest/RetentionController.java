package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.PageResponse;
import co.posinvent.domain.model.ThirdParty;
import co.posinvent.domain.repository.SupplierInvoiceRepository;
import co.posinvent.domain.repository.ThirdPartyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/v1/retenciones")
public class RetentionController {

    private final SupplierInvoiceRepository invoiceRepo;
    private final ThirdPartyRepository thirdPartyRepo;

    public RetentionController(SupplierInvoiceRepository invoiceRepo, ThirdPartyRepository thirdPartyRepo) {
        this.invoiceRepo = invoiceRepo;
        this.thirdPartyRepo = thirdPartyRepo;
    }

    @Transactional(readOnly = true)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','AUXILIAR','CONTADOR')")
    public ResponseEntity<PageResponse<RetentionEntry>> list(
            @RequestParam(required = false) UUID supplierId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var pageable = PageRequest.of(page, size);

        // Aggregate retentions by supplier from invoices
        var invoices = (supplierId != null)
                ? invoiceRepo.findBySupplierId(supplierId, Pageable.unpaged()).getContent()
                : invoiceRepo.findAll(Pageable.unpaged()).getContent();

        var grouped = new LinkedHashMap<UUID, RetentionEntry>();
        for (var inv : invoices) {
            var sid = inv.supplierId();
            var existing = grouped.get(sid);
            var invRet = inv.retentionTotal() != null ? inv.retentionTotal() : BigDecimal.ZERO;
            if (existing != null) {
                grouped.put(sid, new RetentionEntry(sid, existing.supplierName(),
                        existing.invoiceCount() + 1,
                        existing.retentionTotal().add(invRet)));
            } else {
                var name = thirdPartyRepo.findById(sid).map(ThirdParty::name).orElse("Desconocido");
                grouped.put(sid, new RetentionEntry(sid, name, 1, invRet));
            }
        }

        var entries = new ArrayList<>(grouped.values());
        entries.sort((a, b) -> b.retentionTotal().compareTo(a.retentionTotal()));

        // Manual pagination
        var start = page * size;
        var end = Math.min(start + size, entries.size());
        var content = start >= entries.size() ? List.<RetentionEntry>of() : entries.subList(start, end);

        return ResponseEntity.ok(new PageResponse<>(content, page, size, entries.size(),
                (int) Math.ceil((double) entries.size() / size), end >= entries.size()));
    }

    public record RetentionEntry(
            UUID supplierId,
            String supplierName,
            int invoiceCount,
            BigDecimal retentionTotal
    ) {}
}
