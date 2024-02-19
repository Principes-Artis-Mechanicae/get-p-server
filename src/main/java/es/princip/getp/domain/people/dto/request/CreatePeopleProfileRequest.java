package es.princip.getp.domain.people.dto.request;

import java.util.List;

import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.entity.PeopleProfile;
import jakarta.validation.constraints.NotNull;

public record CreatePeopleProfileRequest(@NotNull String introduction, @NotNull String activityArea,
                                            @NotNull List<String> techStacks, @NotNull String education, @NotNull List<String> hashtags) {

    public PeopleProfile toEntity(final People people) {
        return PeopleProfile.builder()
            .introduction(introduction)
            .activityArea(activityArea)
            .education(education)
            .hashtags(hashtags)
            .techStacks(techStacks)
            .people(people)
            .build();
    }
}