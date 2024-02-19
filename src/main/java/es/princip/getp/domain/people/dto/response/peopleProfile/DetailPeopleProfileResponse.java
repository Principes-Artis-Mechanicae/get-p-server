package es.princip.getp.domain.people.dto.response.peopleProfile;

import java.util.List;
import es.princip.getp.domain.hashtag.entity.Hashtag;
import es.princip.getp.domain.people.entity.PeopleProfile;
import es.princip.getp.domain.techStack.entity.TechStack;
import jakarta.validation.constraints.NotNull;

public record DetailPeopleProfileResponse(@NotNull String introduction, @NotNull String activityArea,
                                            @NotNull List<String> techStacks, @NotNull String education, @NotNull List<String> hashtags,
                                            @NotNull Integer completedProjectsCount, @NotNull Integer interestsCount) {

    public static DetailPeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new DetailPeopleProfileResponse(
            peopleProfile.getIntroduction(),
            peopleProfile.getActivityArea(),
            peopleProfile.getTechStacks().stream().map(TechStack::getValue).toList(),
            peopleProfile.getEducation(),
            peopleProfile.getHashtags().stream().map(Hashtag::getValue).toList(),
            //저장 프로퍼티 구현
            0,0
        );
    }
}