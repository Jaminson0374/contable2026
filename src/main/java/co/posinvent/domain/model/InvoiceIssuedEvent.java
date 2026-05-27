package co.posinvent.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class InvoiceIssuedEvent {

    private final UUID salesDocumentId;
    private final String invoiceNumber;
    private final BigDecimal subtotal;
    private final BigDecimal taxAmount;
    private final BigDecimal total;
    private final Object source;

    public InvoiceIssuedEvent(Object source, UUID salesDocumentId, String invoiceNumber,
                               BigDecimal subtotal, BigDecimal taxAmount, BigDecimal total) {
        this.source = source;
        this.salesDocumentId = salesDocumentId;
        this.invoiceNumber = invoiceNumber;
        this.subtotal = subtotal;
        this.taxAmount = taxAmount;
        this.total = total;
    }

    public Object getSource() { return source; }
    public UUID salesDocumentId() { return salesDocumentId; }
    public String invoiceNumber() { return invoiceNumber; }
    public BigDecimal subtotal() { return subtotal; }
    public BigDecimal taxAmount() { return taxAmount; }
    public BigDecimal total() { return total; }
}
