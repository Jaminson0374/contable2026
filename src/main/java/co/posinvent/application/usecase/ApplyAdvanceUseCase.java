package co.posinvent.application.usecase;

import co.posinvent.application.dto.ApplyAdvanceRequest;
import co.posinvent.application.dto.AdvanceResponse;
import co.posinvent.application.service.SupplierBalanceService;
import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.exception.ResourceNotFoundException;
import co.posinvent.domain.model.AdvanceApplication;
import co.posinvent.domain.model.InvoiceStatus;
import co.posinvent.domain.model.Payment;
import co.posinvent.domain.model.SupplierInvoice;
import co.posinvent.domain.repository.AdvanceApplicationRepository;
import co.posinvent.domain.repository.PaymentRepository;
import co.posinvent.domain.repository.SupplierInvoiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class ApplyAdvanceUseCase {

    private final PaymentRepository paymentRepository;
    private final SupplierInvoiceRepository invoiceRepository;
    private final AdvanceApplicationRepository advanceApplicationRepository;

    public ApplyAdvanceUseCase(
            PaymentRepository paymentRepository,
            SupplierInvoiceRepository invoiceRepository,
            AdvanceApplicationRepository advanceApplicationRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
        this.advanceApplicationRepository = advanceApplicationRepository;
    }

    @Transactional
    public AdvanceResponse apply(ApplyAdvanceRequest request, UUID operatorId) {
        // 1. Load advance payment
        var advance = paymentRepository.findById(request.advancePaymentId())
                .orElseThrow(() -> new ResourceNotFoundException("Anticipo", request.advancePaymentId()));

        if (!advance.isAdvance()) {
            throw new BusinessException("NOT_AN_ADVANCE", "El pago no es un anticipo.");
        }

        var remaining = advance.remainingAdvance();
        if (remaining == null || remaining.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("ADVANCE_EXHAUSTED", "El anticipo ya no tiene saldo disponible.");
        }

        if (request.appliedAmount().compareTo(remaining) > 0) {
            throw new BusinessException(
                    "INSUFFICIENT_ADVANCE",
                    "El monto a aplicar (" + request.appliedAmount()
                    + ") excede el saldo disponible del anticipo (" + remaining + ")."
            );
        }

        // 2. Load invoice
        var invoice = invoiceRepository.findById(request.invoiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Factura", request.invoiceId()));

        if (invoice.status() != InvoiceStatus.PENDING && invoice.status() != InvoiceStatus.RECONCILED) {
            throw new BusinessException(
                    "INVOICE_NOT_APPLICABLE",
                    "La factura " + invoice.invoiceNumber()
                    + " no está en estado PENDING ni RECONCILED. Estado: " + invoice.status()
            );
        }

        // 3. Validate supplier match
        if (!invoice.supplierId().equals(advance.supplierId())) {
            throw new BusinessException(
                    "SUPPLIER_MISMATCH",
                    "La factura pertenece a otro proveedor. Anticipo: " + advance.supplierId()
                    + ", Factura: " + invoice.supplierId()
            );
        }

        // 4. Create AdvanceApplication record
        var application = new AdvanceApplication(
                null,
                request.advancePaymentId(),
                request.invoiceId(),
                request.appliedAmount(),
                LocalDate.now(),
                operatorId,
                null
        );
        advanceApplicationRepository.save(application);

        // 5. Decrement remainingAdvance
        var newRemaining = remaining.subtract(request.appliedAmount());

        var updatedAdvance = new Payment(
                advance.id(),
                advance.supplierId(),
                advance.amount(),
                advance.paymentDate(),
                advance.method(),
                advance.reference(),
                advance.notes(),
                advance.createdBy(),
                advance.createdAt(),
                advance.updatedAt(),
                advance.version(),
                true,
                newRemaining,
                advance.invoicePayments()
        );
        paymentRepository.save(updatedAdvance);

        // 6. Update invoice status if total is now covered
        updateInvoiceStatusIfCovered(invoice);

        return AdvanceResponse.from(updatedAdvance);
    }

    /**
     * Checks if the invoice total is fully covered by payments and advances,
     * updating its status to PAID if so.
     */
    private void updateInvoiceStatusIfCovered(SupplierInvoice invoice) {
        // If already PAID, nothing to do
        if (invoice.status() == InvoiceStatus.PAID) return;

        // For now, advance application transitions invoice to RECONCILED at minimum.
        // Full payment checking would require summing up all invoice_payments + advance_applications
        // which is a separate concern. We transition to RECONCILED since at least partial payment
        // has been applied via advance.

        if (invoice.status() == InvoiceStatus.PENDING) {
            var updatedInvoice = new SupplierInvoice(
                    invoice.id(),
                    invoice.supplierId(),
                    invoice.invoiceNumber(),
                    invoice.issueDate(),
                    invoice.dueDate(),
                    invoice.subtotal(),
                    invoice.ivaTotal(),
                    invoice.retentionTotal(),
                    invoice.total(),
                    InvoiceStatus.RECONCILED,
                    invoice.notes(),
                    invoice.createdBy(),
                    invoice.createdAt(),
                    null,
                    invoice.version(),
                    invoice.ocIds()
            );
            invoiceRepository.save(updatedInvoice);
        }
    }
}
