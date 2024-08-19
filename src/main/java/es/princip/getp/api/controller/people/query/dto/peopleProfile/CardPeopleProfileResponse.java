package es.princip.getp.api.controller.people.query.dto.peopleProfile;

import es.princip.getp.common.dto.HashtagsResponse;
import es.princip.getp.domain.people.command.domain.PeopleProfile;

public record CardPeopleProfileResponse(
    String introduction,
    String activityArea,
    HashtagsResponse hashtags
) {

    public static CardPeopleProfileResponse from(final PeopleProfile profile) {
        return new CardPeopleProfileResponse(
            profile.getIntroduction(),
            profile.getActivityArea(),
            HashtagsResponse.from(profile.getHashtags())
        );
    }
}
