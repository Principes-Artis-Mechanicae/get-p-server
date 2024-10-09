package es.princip.getp.api.controller.people.query.dto.peopleProfile;

import es.princip.getp.domain.common.model.Hashtag;
import es.princip.getp.domain.people.model.PeopleProfile;

import java.util.List;

public record PublicDetailPeopleProfileResponse(
    List<String> hashtags
) {
    public static PublicDetailPeopleProfileResponse from(final PeopleProfile profile) {
        return new PublicDetailPeopleProfileResponse(
            profile.getHashtags().stream()
                .map(Hashtag::getValue)
                .toList()
        );
    }
}