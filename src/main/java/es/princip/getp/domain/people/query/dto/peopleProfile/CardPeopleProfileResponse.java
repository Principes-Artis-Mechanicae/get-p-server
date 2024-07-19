package es.princip.getp.domain.people.query.dto.peopleProfile;

import es.princip.getp.domain.common.dto.HashtagsResponse;
import es.princip.getp.domain.people.command.domain.PeopleProfile;

public record CardPeopleProfileResponse(
    String activityArea,
    HashtagsResponse hashtags
) {

    public static CardPeopleProfileResponse from(final PeopleProfile profile) {
        return new CardPeopleProfileResponse(
            profile.getActivityArea(),
            HashtagsResponse.from(profile.getHashtags())
        );
    }
}
