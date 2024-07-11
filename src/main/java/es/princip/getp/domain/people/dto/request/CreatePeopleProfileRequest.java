package es.princip.getp.domain.people.dto.request;

import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleProfile;
import es.princip.getp.domain.people.domain.Education;
import es.princip.getp.domain.people.domain.Portfolio;
import es.princip.getp.global.domain.Hashtag;
import es.princip.getp.global.domain.TechStack;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreatePeopleProfileRequest(
    @Valid Education education,
    @NotNull String activityArea,
    String introduction,
    @Valid List<TechStack> techStacks,
    @Valid List<Portfolio> portfolios,
    @Valid List<Hashtag> hashtags
) {

    public PeopleProfile toEntity(final People people) {
        return PeopleProfile.builder()
            .introduction(introduction)
            .activityArea(activityArea)
            .education(education)
            .hashtags(hashtags)
            .techStacks(techStacks)
            .portfolios(portfolios)
            .people(people)
            .build();
    }
}