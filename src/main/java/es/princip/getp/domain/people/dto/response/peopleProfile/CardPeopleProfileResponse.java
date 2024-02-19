package es.princip.getp.domain.people.dto.response.peopleProfile;

import java.util.List;
import es.princip.getp.domain.hashtag.entity.Hashtag;
import es.princip.getp.domain.people.entity.PeopleProfile;
import jakarta.validation.constraints.NotNull;

public record CardPeopleProfileResponse(@NotNull String activityArea, @NotNull List<String> hashtags,
                                @NotNull Integer completedProjectsCount, @NotNull Integer interestsCount) {

    public static CardPeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new CardPeopleProfileResponse(
            peopleProfile.getActivityArea(),
            peopleProfile.getHashtags().stream().map(Hashtag::getValue).toList(),
            //저장 프로퍼티 구현
            0,0
        );
    }
}