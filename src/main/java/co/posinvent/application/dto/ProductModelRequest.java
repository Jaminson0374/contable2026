package co.posinvent.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record ProductModelRequest(
    @NotBlank @Size(max = 100) String name,
    UUID brandId
) {}
