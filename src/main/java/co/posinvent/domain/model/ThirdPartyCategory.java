package co.posinvent.domain.model;

import java.util.UUID;

public record ThirdPartyCategory(UUID id, String name, String baseType, boolean active) {}
