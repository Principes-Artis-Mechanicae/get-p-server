package es.princip.getp.api.controller.people.query.dto.peopleProfile;

import es.princip.getp.api.controller.common.dto.HashtagsResponse;
import es.princip.getp.domain.people.model.PeopleProfile;

public record PublicDetailPeopleProfileResponse(
    HashtagsResponse hashtags
) {

    public static PublicDetailPeopleProfileResponse from(final PeopleProfile profile) {
        return new PublicDetailPeopleProfileResponse(
            HashtagsResponse.from(profile.getHashtags())
        );
    }
}