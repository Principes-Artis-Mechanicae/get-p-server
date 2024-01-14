package es.princip.getp.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
    @NotNull String email,
    @NotNull String password
) {
}