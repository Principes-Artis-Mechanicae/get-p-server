package es.princip.getp.domain.serviceTerm.dto.reqeust;

import jakarta.validation.constraints.NotNull;

public record ServiceTermAgreementRequest(
    @NotNull String tag, 
    @NotNull boolean agreed
) {
}