package co.posinvent.application.usecase;

import co.posinvent.application.dto.CustomerStatementResponse;
import co.posinvent.application.dto.StatementEntry;
import co.posinvent.domain.exception.ResourceNotFoundException;
import co.posinvent.domain.repository.CustomerReceiptRepository;
import co.posinvent.domain.repository.SalesDocumentRepository;
import co.posinvent.domain.repository.ThirdPartyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerStatementUseCase {

    private final SalesDocumentRepository documentRepo;
    private final CustomerReceiptRepository receiptRepo;
    private final ThirdPartyRepository thirdPartyRepo;

    public CustomerStatementUseCase(
            SalesDocumentRepository documentRepo,
            CustomerReceiptRepository receiptRepo,
            ThirdPartyRepository thirdPartyRepo
    ) {
        this.documentRepo = documentRepo;
        this.receiptRepo = receiptRepo;
        this.thirdPartyRepo = thirdPartyRepo;
    }

    public CustomerStatementResponse generate(UUID clientId, LocalDate from, LocalDate to) {
        var client = thirdPartyRepo.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", clientId));

        var entries = new ArrayList<StatementEntry>();

        // Debits: SalesDocuments of type INVOICE with status ISSUED
        var pageable = PageRequest.of(0, 500, Sort.by("createdAt").ascending());
        var invoices = documentRepo.findByClientId(clientId, pageable);

        invoices.getContent().stream()
                .filter(d -> d.type() != null && d.type().name().equals("INVOICE"))
                .filter(d -> {
                    var invoiceDate = d.createdAt() != null ? d.createdAt().toLocalDate() : null;
                    return invoiceDate != null && !invoiceDate.isBefore(from) && !invoiceDate.isAfter(to);
                })
                .forEach(d -> {
                    var date = d.createdAt() != null ? d.createdAt().toLocalDate() : LocalDate.now();
                    entries.add(new StatementEntry(
                            date,
                            d.documentNumber(),
                            "INVOICE",
                            "Factura de venta",
                            d.totalAmount() != null ? d.totalAmount() : BigDecimal.ZERO,
                            BigDecimal.ZERO,
                            BigDecimal.ZERO
                    ));
                });

        // Credits: CustomerReceipts
        var receipts = receiptRepo.findByClientId(clientId, pageable);
        receipts.getContent().stream()
                .filter(r -> !r.paymentDate().isBefore(from) && !r.paymentDate().isAfter(to))
                .forEach(r -> {
                    entries.add(new StatementEntry(
                            r.paymentDate(),
                            r.reference() != null ? r.reference() : "Recibo",
                            "RECEIPT",
                            "Recibo de caja - " + (r.method() != null ? r.method().name() : ""),
                            BigDecimal.ZERO,
                            r.amount(),
                            BigDecimal.ZERO
                    ));
                });

        // Sort by date
        entries.sort(Comparator.comparing(StatementEntry::date));

        // Calculate running balances
        var running = BigDecimal.ZERO;
        for (int i = 0; i < entries.size(); i++) {
            var e = entries.get(i);
            running = running.add(e.debit()).subtract(e.credit());
            entries.set(i, new StatementEntry(
                    e.date(), e.documentNumber(), e.type(), e.description(),
                    e.debit(), e.credit(), running
            ));
        }

        var openingBalance = BigDecimal.ZERO;
        var closingBalance = running;

        return new CustomerStatementResponse(
                clientId.toString(),
                client.name() + (client.lastName() != null ? " " + client.lastName() : ""),
                from, to,
                openingBalance,
                entries,
                closingBalance
        );
    }
}
