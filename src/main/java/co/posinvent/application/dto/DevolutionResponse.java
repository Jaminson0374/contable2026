package co.posinvent.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DevolutionResponse(
        UUID creditNoteId,
        String documentNumber,
        List<DevolutionItemResponse> items,
        BigDecimal totalReturned,
        boolean stockReversed
) {
    public record DevolutionItemResponse(
            UUID productId,
            BigDecimal quantity,
            BigDecimal unitPrice,
            BigDecimal subtotal
    ) {}
}
