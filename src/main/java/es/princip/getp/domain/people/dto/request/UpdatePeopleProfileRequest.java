package es.princip.getp.domain.people.dto.request;

import es.princip.getp.domain.people.domain.values.Education;
import es.princip.getp.domain.people.domain.values.Portfolio;
import es.princip.getp.global.domain.values.Hashtag;
import es.princip.getp.global.domain.values.TechStack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdatePeopleProfileRequest(
    @Valid Education education,
    @NotNull String activityArea,
    String introduction,
    @Valid List<TechStack> techStacks,
    @Valid List<Portfolio> portfolios,
    @Valid List<Hashtag> hashtags
) {
}