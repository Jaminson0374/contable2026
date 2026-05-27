package co.posinvent.application.service;

import co.posinvent.application.usecase.CreateJournalEntryUseCase;
import co.posinvent.domain.model.InvoiceIssuedEvent;
import co.posinvent.domain.model.JournalEntryLine;
import co.posinvent.domain.repository.PucAccountRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class AccountingEventListener {

    private final CreateJournalEntryUseCase journalUseCase;
    private final PucAccountRepository pucRepo;

    public AccountingEventListener(CreateJournalEntryUseCase journalUseCase, PucAccountRepository pucRepo) {
        this.journalUseCase = journalUseCase;
        this.pucRepo = pucRepo;
    }

    // ── VENTA ──
    @EventListener
    public void onInvoiceIssued(InvoiceIssuedEvent event) {
        var clientAcct = pucRepo.findByCode("1305")
                .orElseThrow();
        var revenueAcct = pucRepo.findByCode("4135")
                .orElseThrow();
        var taxAcct = pucRepo.findByCode("2408")
                .orElseThrow();

        var lines = new ArrayList<JournalEntryLine>();
        lines.add(new JournalEntryLine(null, null, clientAcct.id(), event.total(), BigDecimal.ZERO, "Venta " + event.invoiceNumber()));
        lines.add(new JournalEntryLine(null, null, revenueAcct.id(), BigDecimal.ZERO, event.subtotal(), "Ingreso venta " + event.invoiceNumber()));
        if (event.taxAmount().compareTo(BigDecimal.ZERO) > 0) {
            lines.add(new JournalEntryLine(null, null, taxAcct.id(), BigDecimal.ZERO, event.taxAmount(), "IVA venta " + event.invoiceNumber()));
        }
        journalUseCase.createAuto("SALE", event.salesDocumentId(), LocalDate.now(), "Factura de venta " + event.invoiceNumber(), lines);
    }

    // ── PAGO RECIBIDO ──
    @EventListener
    public void onCashReceipt(co.posinvent.domain.model.CashReceiptEvent event) {
        var cashAcct = pucRepo.findByCode("1105").orElseThrow();
        var clientAcct = pucRepo.findByCode("1305").orElseThrow();

        var lines = new ArrayList<JournalEntryLine>();
        lines.add(new JournalEntryLine(null, null, cashAcct.id(), event.amount(), BigDecimal.ZERO, "Recibo " + event.receiptNumber()));
        lines.add(new JournalEntryLine(null, null, clientAcct.id(), BigDecimal.ZERO, event.amount(), "Abono cliente recibo " + event.receiptNumber()));
        journalUseCase.createAuto("PAYMENT", event.receiptId(), LocalDate.now(), "Recibo de caja " + event.receiptNumber(), lines);
    }

    // ── AJUSTE DE INVENTARIO ──
    @EventListener
    public void onAdjustment(co.posinvent.domain.model.AdjustmentAppliedEvent event) {
        var inventoryAcct = pucRepo.findByCode("1435").orElseThrow();
        var lossAcct = pucRepo.findByCode("5195").orElseThrow();

        var lines = new ArrayList<JournalEntryLine>();
        if (event.delta().compareTo(BigDecimal.ZERO) < 0) {
            var absDelta = event.delta().abs();
            lines.add(new JournalEntryLine(null, null, lossAcct.id(), absDelta, BigDecimal.ZERO, "Pérdida inventario " + event.reason()));
            lines.add(new JournalEntryLine(null, null, inventoryAcct.id(), BigDecimal.ZERO, absDelta, "Ajuste inventario " + event.reason()));
        } else {
            lines.add(new JournalEntryLine(null, null, inventoryAcct.id(), event.delta(), BigDecimal.ZERO, "Sobrante inventario " + event.reason()));
            lines.add(new JournalEntryLine(null, null, lossAcct.id(), BigDecimal.ZERO, event.delta(), "Ingreso sobrante " + event.reason()));
        }
        journalUseCase.createAuto("INVENTORY", event.adjustmentId(), LocalDate.now(), "Ajuste inventario: " + event.reason(), lines);
    }

    // ── COMPRA ──
    @EventListener
    public void onPurchase(co.posinvent.domain.model.PurchaseAccountedEvent event) {
        var inventoryAcct = pucRepo.findByCode("1435").orElseThrow();
        var supplierAcct = pucRepo.findByCode("2205").orElseThrow();

        var lines = new ArrayList<JournalEntryLine>();
        lines.add(new JournalEntryLine(null, null, inventoryAcct.id(), event.subtotal(), BigDecimal.ZERO, "Compra factura " + event.invoiceNumber()));
        lines.add(new JournalEntryLine(null, null, supplierAcct.id(), BigDecimal.ZERO, event.netPayable(), "Proveedor factura " + event.invoiceNumber()));

        var withholdingFuente = pucRepo.findByCode("2365");
        var withholdingIca = pucRepo.findByCode("2368");
        if (event.retefuente().compareTo(BigDecimal.ZERO) > 0 && withholdingFuente.isPresent()) {
            lines.add(new JournalEntryLine(null, null, withholdingFuente.get().id(), BigDecimal.ZERO, event.retefuente(), "Retefuente " + event.invoiceNumber()));
        }
        if (event.ica().compareTo(BigDecimal.ZERO) > 0 && withholdingIca.isPresent()) {
            lines.add(new JournalEntryLine(null, null, withholdingIca.get().id(), BigDecimal.ZERO, event.ica(), "ICA " + event.invoiceNumber()));
        }
        journalUseCase.createAuto("PURCHASE", event.invoiceId(), LocalDate.now(), "Factura proveedor " + event.invoiceNumber(), lines);
    }
}
