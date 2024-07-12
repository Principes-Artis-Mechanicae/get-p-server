package es.princip.getp.domain.auth.presentation.dto.request;

import jakarta.validation.constraints.NotNull;

public record ServiceTermAgreementRequest(
    @NotNull String tag, 
    @NotNull boolean agreed
) {
}