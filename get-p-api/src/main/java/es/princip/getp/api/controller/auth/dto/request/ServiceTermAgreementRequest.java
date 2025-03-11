package es.princip.getp.api.controller.auth.dto.request;

import jakarta.validation.constraints.NotNull;

public record ServiceTermAgreementRequest(
    @NotNull String tag, 
    @NotNull Boolean agreed
) {
}