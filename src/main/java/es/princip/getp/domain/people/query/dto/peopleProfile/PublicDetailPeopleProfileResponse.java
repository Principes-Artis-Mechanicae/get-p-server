package es.princip.getp.domain.people.query.dto.peopleProfile;

import es.princip.getp.domain.common.dto.HashtagsResponse;
import es.princip.getp.domain.people.command.domain.PeopleProfile;

public record PublicDetailPeopleProfileResponse(
    Integer completedProjectsCount,
    Integer interestsCount,
    HashtagsResponse hashtags
) {

    public static PublicDetailPeopleProfileResponse from(final PeopleProfile profile) {
        return new PublicDetailPeopleProfileResponse(
            0,
            0,
            HashtagsResponse.from(profile.getHashtags())
        );
    }
}