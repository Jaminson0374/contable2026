package co.posinvent.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record DianResolutionRequest(
    @NotBlank String resolutionNumber,
    @NotNull LocalDate resolutionDate,
    @NotNull LocalDate validFrom,
    @NotNull LocalDate validTo,
    String prefix,
    @NotNull Long rangeFrom,
    @NotNull Long rangeTo,
    String softwarePin,
    Boolean active
) {}
