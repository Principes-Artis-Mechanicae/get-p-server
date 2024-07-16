package es.princip.getp.domain.people.command.presentation.dto.request;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.common.domain.TechStack;
import es.princip.getp.domain.people.command.domain.Education;
import es.princip.getp.domain.people.command.domain.Portfolio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record WritePeopleProfileRequest(
    @NotNull @Valid Education education,
    @NotBlank String activityArea,
    @NotBlank String introduction,
    @NotNull @Valid List<TechStack> techStacks,
    @NotNull @Valid List<Portfolio> portfolios,
    @NotNull @Valid List<Hashtag> hashtags
) {
}