package es.princip.getp.domain.people.dto.response.peopleProfile;

import java.util.List;
import es.princip.getp.domain.hashtag.values.Hashtag;
import es.princip.getp.domain.people.entity.PeopleProfile;
import es.princip.getp.domain.techStack.entity.TechStack;
import jakarta.validation.constraints.NotNull;

public record CreatePeopleProfileResponse(@NotNull String introduction,
                                          @NotNull String activityArea,
                                          @NotNull List<String> techStacks,
                                          @NotNull String education,
                                          @NotNull List<String> hashtags) {

    public static CreatePeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new CreatePeopleProfileResponse(
            peopleProfile.getIntroduction(),
            peopleProfile.getActivityArea(),
            peopleProfile.getTechStacks().stream().map(TechStack::getValue).toList(),
            peopleProfile.getEducation(),
            peopleProfile.getHashtags().stream().map(Hashtag::getValue).toList()
        );
    }
}