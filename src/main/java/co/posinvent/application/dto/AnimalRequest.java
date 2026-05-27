package co.posinvent.application.dto;

import co.posinvent.domain.model.Animal.Species;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record AnimalRequest(
        @NotBlank @Size(max = 50) String icaLotNumber,
        @NotNull UUID supplierId,
        @NotNull Species species,
        @NotNull @DecimalMin("0.1") BigDecimal liveWeight,
        @NotNull LocalDate receptionDate,
        @Size(max = 500) String notes
) {}
