package co.posinvent.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ThirdPartyCategoryRequest(
        @NotBlank @Size(max = 50) String name,
        @NotBlank String baseType
) {}
