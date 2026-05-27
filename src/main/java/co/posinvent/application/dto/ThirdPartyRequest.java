package co.posinvent.application.dto;

import co.posinvent.domain.model.ThirdParty.PersonType;
import co.posinvent.domain.model.ThirdParty.TaxRegime;
import co.posinvent.domain.model.ThirdParty.ThirdPartyType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ThirdPartyRequest(
        @NotBlank @Size(max = 40)  String numIdentification,
        @NotBlank @Size(max = 200) String name,
        ThirdPartyType type,
        UUID priceListId,
        @NotNull @DecimalMin("0") BigDecimal creditLimit,
        @NotNull PersonType personType,
        @NotNull TaxRegime taxRegime,
        @NotNull List<String> taxResponsibilities,

        @Size(max = 10) String cityCode,
        @Size(max = 10) String dianClassification,

        UUID thirdPartyCategoryId,
        UUID identificationTypeId,
        String dv,
        String lastName,
        String commonName,
        String phone,
        String address,
        UUID departmentId,
        UUID cityId,
        String email,
        String website,
        LocalDate entryDate,
        int creditDays,

        String contactName,
        String contactPhone,
        String contactAddress,
        String contactEmail,

        String taxContactFirstName,
        String taxContactLastName,
        String taxEmail,
        String billingPhone,

        boolean isGranContribuyente,
        boolean isAutoretenedor,
        boolean isAgenteRetencionIva,
        boolean isRegimenSimple,
        boolean otherTaxResp,

        EmployeeBasicDataRequest employeeData
) {}
