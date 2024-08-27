package es.princip.getp.api.controller.people.command.dto.request;

import es.princip.getp.api.validation.URLPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PortfolioRequest(
    @NotBlank String description,
    @NotNull @URLPattern String url
) {
}