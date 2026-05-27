package co.posinvent.application.usecase;

import co.posinvent.domain.model.SalesDocument;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckoutResponse(
        UUID invoiceId,
        String documentNumber,
        BigDecimal totalAmount,
        BigDecimal changeAmount
) {
    public static CheckoutResponse from(SalesDocument invoice, BigDecimal change) {
        return new CheckoutResponse(
                invoice.id(),
                invoice.documentNumber(),
                invoice.totalAmount(),
                change
        );
    }
}
