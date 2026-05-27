package co.posinvent.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CustomPriceRequest(
        @NotNull(message = "clientId is required") UUID clientId,
        @NotNull(message = "productId is required") UUID productId,
        @NotNull(message = "price is required")
        @DecimalMin(value = "0.01", message = "price must be > 0")
        BigDecimal price,
        @NotBlank(message = "taxType is required") String taxType,
        @NotNull(message = "taxRate is required")
        @DecimalMin(value = "0", message = "taxRate must be >= 0")
        BigDecimal taxRate
) {}