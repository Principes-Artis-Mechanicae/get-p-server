package es.princip.getp.domain.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record EmailVerificationCodeRequest(@NotNull String email) {
}