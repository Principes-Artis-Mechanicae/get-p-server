package es.princip.getp.domain.people.command.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PortfolioRequest(
    @NotBlank String description,
    @NotBlank String url
) {
}