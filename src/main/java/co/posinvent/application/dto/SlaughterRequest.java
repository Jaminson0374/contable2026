package co.posinvent.application.dto;

import co.posinvent.domain.model.Slaughter.SlaughterSourceType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record SlaughterRequest(
        @NotNull UUID animalId,
        @NotNull SlaughterSourceType sourceType,
        @Size(max = 300) String justification,
        @NotNull @DecimalMin("0.1") BigDecimal carcassWeight,
        @NotNull @DecimalMin("0.01") BigDecimal purchaseCost,
        @NotBlank @Size(max = 100) String invimaPlant,
        @NotNull UUID inspectorId,
        @NotNull LocalDate slaughterDate,
        @Size(max = 500) String notes
) {}
