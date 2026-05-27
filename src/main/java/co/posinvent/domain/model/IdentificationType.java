package co.posinvent.domain.model;

import java.util.UUID;

public record IdentificationType(UUID id, String code, String name, boolean requiresDv, boolean active) {}
