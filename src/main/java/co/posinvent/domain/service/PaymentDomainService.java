package co.posinvent.domain.service;

import co.posinvent.domain.exception.BusinessException;
import co.posinvent.domain.model.InvoiceStatus;
import co.posinvent.domain.model.Payment.InvoicePayment;
import co.posinvent.domain.model.SupplierInvoice;

import java.math.BigDecimal;
import java.util.List;

/**
 * Validación pura de dominio para pagos.
 * No tiene dependencias de infraestructura ni Spring.
 */
public final class PaymentDomainService {

    /**
     * Validates that the payment amount is greater than zero.
     *
     * @throws BusinessException with code INVALID_PAYMENT_AMOUNT
     */
    public void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(
                    "INVALID_PAYMENT_AMOUNT",
                    "El monto del pago debe ser mayor a cero"
            );
        }
    }

    /**
     * Validates that all invoices are payable and the payment amounts don't exceed
     * the invoice totals.
     *
     * @param invoices         the invoices being paid
     * @param invoicePayments  how the payment is split across invoices
     * @param totalAmount      the total payment amount
     * @throws BusinessException with code INVOICE_NOT_PAYABLE or OVERPAYMENT
     */
    public void validateInvoicePayments(
            List<SupplierInvoice> invoices,
            List<InvoicePayment> invoicePayments,
            BigDecimal totalAmount
    ) {
        // Each invoice must be in RECONCILED status
        for (var invoice : invoices) {
            if (invoice.status() != InvoiceStatus.RECONCILED) {
                throw new BusinessException(
                        "INVOICE_NOT_PAYABLE",
                        "La factura " + invoice.invoiceNumber() +
                        " no está en estado RECONCILED. Estado: " + invoice.status()
                );
            }
        }

        // Sum of applied amounts must equal total payment amount (within 0.01 tolerance)
        var appliedSum = invoicePayments.stream()
                .map(InvoicePayment::appliedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        var diff = totalAmount.subtract(appliedSum).abs();
        if (diff.compareTo(new BigDecimal("0.01")) > 0) {
            throw new BusinessException(
                    "INVALID_PAYMENT_AMOUNT",
                    "La suma de montos aplicados (" + appliedSum +
                    ") no coincide con el total del pago (" + totalAmount + ")"
            );
        }

        // No single invoice overpaid
        var invoiceById = invoices.stream()
                .collect(java.util.stream.Collectors.toMap(
                        SupplierInvoice::id, inv -> inv));

        for (var ip : invoicePayments) {
            var inv = invoiceById.get(ip.invoiceId());
            if (inv == null) continue;

            // Amount being applied must be > 0
            if (ip.appliedAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new BusinessException(
                        "INVALID_PAYMENT_AMOUNT",
                        "El monto aplicado a la factura " + inv.invoiceNumber() +
                        " debe ser mayor a cero"
                );
            }

            // Cannot pay more than invoice total
            if (ip.appliedAmount().compareTo(inv.total()) > 0) {
                throw new BusinessException(
                        "OVERPAYMENT",
                        "El monto aplicado (" + ip.appliedAmount() +
                        ") excede el total de la factura " + inv.invoiceNumber() +
                        " (" + inv.total() + ")"
                );
            }
        }
    }
}
