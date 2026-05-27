package co.posinvent.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record DevolutionRequest(
        @NotNull UUID invoiceId,
        @NotNull @Size(min = 1) List<DevolutionItem> items,
        @NotBlank String reason
) {
    public record DevolutionItem(
            @NotNull UUID productId,
            @NotNull BigDecimal quantity
    ) {}
}
