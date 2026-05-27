package co.posinvent.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Machinery(
    UUID id,
    String code,
    String name,
    String machineryType,
    String status,
    OffsetDateTime createdAt
) {}