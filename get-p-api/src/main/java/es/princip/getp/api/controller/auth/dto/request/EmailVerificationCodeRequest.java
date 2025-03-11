package es.princip.getp.api.controller.auth.dto.request;

import es.princip.getp.domain.common.model.EmailPattern;
import jakarta.validation.constraints.NotNull;

public record EmailVerificationCodeRequest(
    @NotNull @EmailPattern String email
) {
}
