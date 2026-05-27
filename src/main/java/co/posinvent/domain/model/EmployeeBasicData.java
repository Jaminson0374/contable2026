package co.posinvent.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public record EmployeeBasicData(
        UUID thirdPartyId,
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
