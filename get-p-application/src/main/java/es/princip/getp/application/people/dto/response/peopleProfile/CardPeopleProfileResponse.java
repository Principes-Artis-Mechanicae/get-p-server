package es.princip.getp.application.people.dto.response.peopleProfile;

import es.princip.getp.domain.common.model.Hashtag;
import es.princip.getp.domain.people.model.PeopleProfile;

import java.util.List;

public record CardPeopleProfileResponse(
    String introduction,
    String activityArea,
    List<String> hashtags
) {
    public static CardPeopleProfileResponse from(final PeopleProfile profile) {
        return new CardPeopleProfileResponse(
            profile.getIntroduction(),
            profile.getActivityArea(),
            profile.getHashtags().stream()
                .map(Hashtag::getValue)
                .toList()
        );
    }
}
