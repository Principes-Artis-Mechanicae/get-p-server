package es.princip.getp.domain.people.command.presentation.dto.request;

import es.princip.getp.domain.people.command.domain.Education;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EditPeopleProfileRequest(
    @NotNull @Valid Education education,
    @NotBlank String activityArea,
    @NotBlank String introduction,
    @NotNull List<String> techStacks,
    @NotNull @Valid List<PortfolioRequest> portfolios,
    @NotNull List<String> hashtags
) {
}