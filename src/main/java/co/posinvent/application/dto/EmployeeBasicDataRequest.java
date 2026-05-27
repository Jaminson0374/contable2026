package co.posinvent.application.dto;

import java.time.LocalDate;

public record EmployeeBasicDataRequest(
        String position,
        String costCenter,
        String workCenter,
        String gender,
        String civilStatus,
        String salesGroup,
        LocalDate birthDate,
        String birthPlace,
        String militaryId,
        boolean isForeigner,
        boolean natResidentExterior,
        boolean isDeclarant,
        String associatedSeller,
        boolean requiresEndowment,
        boolean isSenior
) {}
