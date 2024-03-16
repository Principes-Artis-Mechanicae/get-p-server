package es.princip.getp.domain.people.dto.request;

import java.util.List;

import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.dto.PortfolioForm;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CreatePeopleProfileRequest(@NotNull String introduction, @NotNull String activityArea,
                                            @NotNull List<String> techStacks, @NotNull String education, @NotNull List<String> hashtags,
                                            @Valid List<PortfolioForm> portfolios) {

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