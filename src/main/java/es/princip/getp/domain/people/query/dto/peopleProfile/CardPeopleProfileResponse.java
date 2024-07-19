package es.princip.getp.domain.people.query.dto.peopleProfile;

import es.princip.getp.domain.common.dto.HashtagsResponse;
import es.princip.getp.domain.people.command.domain.PeopleProfile;

public record CardPeopleProfileResponse(
    String activityArea,
    Integer completedProjectsCount,
    Integer interestsCount,
    HashtagsResponse hashtags
) {

    public static CardPeopleProfileResponse from(final PeopleProfile profile) {
        return new CardPeopleProfileResponse(
            profile.getActivityArea(),
            0,
            0,
            HashtagsResponse.from(profile.getHashtags())
        );
    }
}
