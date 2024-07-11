package es.princip.getp.domain.auth.dto.request;

import es.princip.getp.domain.auth.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
    @NotNull @Email String email,
    @NotNull @Password String password
) {
}