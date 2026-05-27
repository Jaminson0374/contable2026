package co.posinvent.application.dto;

import co.posinvent.domain.model.ThirdParty;
import co.posinvent.domain.model.ThirdParty.ThirdPartyType;

import java.util.UUID;

public record ThirdPartySupplierOptionResponse(
        UUID id,
        String name,
        String lastName,
        String numIdentification,
        ThirdPartyType type,
        boolean active
) {
    public static ThirdPartySupplierOptionResponse from(ThirdParty thirdParty) {
        return new ThirdPartySupplierOptionResponse(
                thirdParty.id(),
                thirdParty.name(),
                thirdParty.lastName(),
                thirdParty.numIdentification(),
                thirdParty.type(),
                thirdParty.active()
        );
    }
}
