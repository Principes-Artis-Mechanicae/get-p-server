package es.princip.getp.api.controller.people.command.dto.request;

import es.princip.getp.domain.people.command.domain.Education;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegisterPeopleProfileRequest(
    @NotNull @Valid Education education,
    @NotBlank String activityArea,
    @NotBlank String introduction,
    @NotNull List<@NotBlank String> techStacks,
    @NotNull @Valid List<PortfolioRequest> portfolios,
    @NotNull List<@NotBlank String> hashtags
) {
}