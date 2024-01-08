package es.princip.getp.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record EmailVerificationRequest(@NotNull String email, @NotNull String code) {
}
