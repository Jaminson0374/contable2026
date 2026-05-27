package co.posinvent.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPresentationRequest(
        @NotBlank @Size(max = 20) String code,
        @NotBlank @Size(max = 100) String name,
        @NotNull UUID unitOfMeasureId,
        @NotNull @DecimalMin(value = "0.0001", inclusive = true) BigDecimal conversionFactor,
        BigDecimal salePrice,
        boolean isDefault
) {}
