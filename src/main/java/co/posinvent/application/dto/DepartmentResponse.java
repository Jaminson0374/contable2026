package co.posinvent.application.dto;

import co.posinvent.domain.model.Department;

import java.util.UUID;

public record DepartmentResponse(UUID id, String code, String name) {

    public static DepartmentResponse from(Department d) {
        return new DepartmentResponse(d.id(), d.code(), d.name());
    }
}
