package es.princip.getp.api.controller.people.command.dto.request;

import es.princip.getp.domain.people.model.Education;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EditPeopleProfileRequest(
    @NotNull @Valid Education education,
    @NotBlank String activityArea,
    String introduction,
    List<@NotBlank String> techStacks,
    List<@Valid PortfolioRequest> portfolios,
    List<@NotBlank String> hashtags
) {
}