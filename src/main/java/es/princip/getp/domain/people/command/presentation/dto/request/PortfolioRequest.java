package es.princip.getp.domain.people.command.presentation.dto.request;

import es.princip.getp.domain.common.annotation.URLPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PortfolioRequest(
    @NotBlank String description,
    @NotNull @URLPattern String url
) {
}