package co.posinvent.application.dto;

import co.posinvent.domain.model.City;

import java.util.UUID;

public record CityResponse(UUID id, String code, String name, UUID departmentId) {

    public static CityResponse from(City c) {
        return new CityResponse(c.id(), c.code(), c.name(), c.departmentId());
    }
}
