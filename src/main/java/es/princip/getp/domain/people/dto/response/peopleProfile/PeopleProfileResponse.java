package es.princip.getp.domain.people.dto.response.peopleProfile;

import java.util.List;

import es.princip.getp.domain.people.domain.entity.PeopleHashtag;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.domain.entity.PeopleTechStack;
import jakarta.validation.constraints.NotNull;

public record PeopleProfileResponse(@NotNull String introduction, @NotNull String activityArea,
                                    @NotNull List<String> techStacks, @NotNull String education,
                                    @NotNull List<String> hashtags,
                                    @NotNull Integer completedProjectsCount,
                                    @NotNull Integer interestsCount) {

    public static PeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new PeopleProfileResponse(
            peopleProfile.getIntroduction(),
            peopleProfile.getActivityArea(),
            peopleProfile.getTechStacks().stream().map(PeopleTechStack::getValue).toList(),
            peopleProfile.getEducation(),
            peopleProfile.getHashtags().stream().map(PeopleHashtag::getValue).toList(),
            //저장 프로퍼티 구현
            0, 0
        );
    }
}