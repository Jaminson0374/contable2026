package co.posinvent.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UserRequest(
    @NotBlank @Size(max = 100) String username,
    @NotBlank @Size(max = 200) String fullName,
    @NotBlank @Email @Size(max = 200) String email,
    @NotNull UUID roleId,
    @NotNull Boolean isActive
) {}
