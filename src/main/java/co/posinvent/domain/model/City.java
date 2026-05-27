package co.posinvent.domain.model;

import java.util.UUID;

public record City(UUID id, String code, String name, UUID departmentId) {}
