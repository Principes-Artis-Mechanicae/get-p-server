package es.princip.getp.domain.people.presentation.dto.request;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.people.domain.Education;
import es.princip.getp.domain.people.domain.Portfolio;
import es.princip.getp.domain.project.domain.TechStack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EditPeopleProfileRequest(
    @Valid Education education,
    @NotNull String activityArea,
    String introduction,
    @Valid List<TechStack> techStacks,
    @Valid List<Portfolio> portfolios,
    @Valid List<Hashtag> hashtags
) {
}