package co.posinvent.application.dto;

import co.posinvent.domain.model.IdentificationType;

import java.util.UUID;

public record IdentificationTypeResponse(UUID id, String code, String name, boolean requiresDv) {

    public static IdentificationTypeResponse from(IdentificationType t) {
        return new IdentificationTypeResponse(t.id(), t.code(), t.name(), t.requiresDv());
    }
}
