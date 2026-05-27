package co.posinvent.application.dto;

import co.posinvent.domain.model.ThirdPartyCategory;

import java.util.UUID;

public record ThirdPartyCategoryResponse(UUID id, String name, String baseType, boolean active) {

    public static ThirdPartyCategoryResponse from(ThirdPartyCategory c) {
        return new ThirdPartyCategoryResponse(c.id(), c.name(), c.baseType(), c.active());
    }
}
