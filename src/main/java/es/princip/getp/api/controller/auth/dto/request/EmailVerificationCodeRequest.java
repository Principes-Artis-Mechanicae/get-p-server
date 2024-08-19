package es.princip.getp.api.controller.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record EmailVerificationCodeRequest(
    @NotNull String email
) {
}
