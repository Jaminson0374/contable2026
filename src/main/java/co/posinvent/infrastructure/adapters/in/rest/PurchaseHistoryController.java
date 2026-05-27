package co.posinvent.infrastructure.adapters.in.rest;

import co.posinvent.application.dto.PageResponse;
import co.posinvent.domain.model.*;
import co.posinvent.domain.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/purchase-history")
public class PurchaseHistoryController {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final GoodsReceiptRepository goodsReceiptRepository;
    private final SupplierInvoiceRepository supplierInvoiceRepository;
    private final PaymentRepository paymentRepository;
    private final ThirdPartyRepository thirdPartyRepository;

    public PurchaseHistoryController(
            PurchaseOrderRepository purchaseOrderRepository,
            GoodsReceiptRepository goodsReceiptRepository,
            SupplierInvoiceRepository supplierInvoiceRepository,
            PaymentRepository paymentRepository,
            ThirdPartyRepository thirdPartyRepository
    ) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.goodsReceiptRepository = goodsReceiptRepository;
        this.supplierInvoiceRepository = supplierInvoiceRepository;
        this.paymentRepository = paymentRepository;
        this.thirdPartyRepository = thirdPartyRepository;
    }

    @Transactional(readOnly = true)
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','AUXILIAR','CONTADOR')")
    public ResponseEntity<PageResponse<PurchaseHistoryEntry>> list(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(required = false) UUID supplierId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        var pageable = PageRequest.of(page, size);
        var now = OffsetDateTime.now();
        var entries = new ArrayList<PurchaseHistoryEntry>();
        var supplierNames = new HashMap<UUID, String>();

        // ── Phase 1: collect all supplier IDs ──

        var ocPage = (supplierId != null)
                ? purchaseOrderRepository.findBySupplierId(supplierId, Pageable.unpaged())
                : purchaseOrderRepository.findAll(Pageable.unpaged());
        for (var oc : ocPage.getContent()) {
            supplierNames.put(oc.supplierId(), null);
        }

        var grPage = goodsReceiptRepository.findAll(Pageable.unpaged());
        for (var gr : grPage.getContent()) {
            purchaseOrderRepository.findById(gr.ocId())
                    .map(PurchaseOrder::supplierId)
                    .ifPresent(sid -> supplierNames.put(sid, null));
        }

        var invPage = (supplierId != null)
                ? supplierInvoiceRepository.findBySupplierId(supplierId, Pageable.unpaged())
                : supplierInvoiceRepository.findAll(Pageable.unpaged());
        for (var inv : invPage.getContent()) {
            supplierNames.put(inv.supplierId(), null);
        }

        var pmtPage = (supplierId != null)
                ? paymentRepository.findBySupplierId(supplierId, Pageable.unpaged())
                : paymentRepository.findAll(Pageable.unpaged());
        for (var pmt : pmtPage.getContent()) {
            supplierNames.put(pmt.supplierId(), null);
        }

        // Resolve all names in bulk
        for (var sid : supplierNames.keySet()) {
            if (sid != null) {
                thirdPartyRepository.findById(sid)
                        .map(ThirdParty::name)
                        .ifPresent(name -> supplierNames.put(sid, name));
            }
        }

        // ── Phase 2: build timeline entries ──

        // Purchase Orders
        for (var oc : ocPage.getContent()) {
            var eventDate = oc.orderDate();
            if (!matchesDateRange(eventDate, from, to)) continue;
            entries.add(new PurchaseHistoryEntry("OC", oc.id(), oc.supplierId(),
                    supplierNames.getOrDefault(oc.supplierId(), "Desconocido"),
                    "Orden de compra #" + oc.id(), oc.status().name(), null,
                    eventDate.atStartOfDay().atOffset(now.getOffset())));
        }

        // Goods Receipts
        for (var gr : grPage.getContent()) {
            var eventDate = gr.receiptDate();
            if (!matchesDateRange(eventDate, from, to)) continue;
            var ocSupplierId = purchaseOrderRepository.findById(gr.ocId())
                    .map(PurchaseOrder::supplierId).orElse(null);
            if (supplierId != null && !supplierId.equals(ocSupplierId)) continue;
            entries.add(new PurchaseHistoryEntry("RECEPCION", gr.id(), ocSupplierId,
                    ocSupplierId != null ? supplierNames.getOrDefault(ocSupplierId, "Desconocido") : "Desconocido",
                    "Recepción OC #" + gr.ocId(), gr.status().name(), null,
                    eventDate.atStartOfDay().atOffset(now.getOffset())));
        }

        // Supplier Invoices
        for (var inv : invPage.getContent()) {
            var eventDate = inv.issueDate();
            if (!matchesDateRange(eventDate, from, to)) continue;
            entries.add(new PurchaseHistoryEntry("FACTURA", inv.id(), inv.supplierId(),
                    supplierNames.getOrDefault(inv.supplierId(), "Desconocido"),
                    "Factura " + inv.invoiceNumber(), inv.status().name(), inv.total(),
                    eventDate.atStartOfDay().atOffset(now.getOffset())));
        }

        // Payments
        for (var pmt : pmtPage.getContent()) {
            var eventDate = pmt.paymentDate();
            if (!matchesDateRange(eventDate, from, to)) continue;
            entries.add(new PurchaseHistoryEntry("PAGO", pmt.id(), pmt.supplierId(),
                    supplierNames.getOrDefault(pmt.supplierId(), "Desconocido"),
                    "Pago " + pmt.method() + " ref: " + (pmt.reference() != null ? pmt.reference() : "N/A"),
                    "COMPLETED", pmt.amount(),
                    eventDate.atStartOfDay().atOffset(now.getOffset())));
        }

        // Sort & paginate
        entries.sort(Comparator.comparing(PurchaseHistoryEntry::eventTimestamp).reversed());
        var start = (int) pageable.getOffset();
        var end = Math.min(start + pageable.getPageSize(), entries.size());
        var pageContent = start >= entries.size() ? List.<PurchaseHistoryEntry>of() : entries.subList(start, end);

        return ResponseEntity.ok(new PageResponse<>(pageContent, page, size, entries.size(),
                (int) Math.ceil((double) entries.size() / size), end >= entries.size()));
    }

    private boolean matchesDateRange(LocalDate date, LocalDate from, LocalDate to) {
        if (from == null && to == null) return true;
        if (date == null) return false;
        if (from != null && date.isBefore(from)) return false;
        if (to != null && date.isAfter(to)) return false;
        return true;
    }

    public record PurchaseHistoryEntry(String eventType, UUID documentId, UUID supplierId,
            String supplierName, String description, String status, java.math.BigDecimal amount,
            OffsetDateTime eventTimestamp) {}
}
