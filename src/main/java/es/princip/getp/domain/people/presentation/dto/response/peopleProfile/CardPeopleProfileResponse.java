package es.princip.getp.domain.people.presentation.dto.response.peopleProfile;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.people.domain.PeopleProfile;

import java.util.List;

public record CardPeopleProfileResponse(
    String activityArea,
    List<Hashtag> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount
) {

    public static CardPeopleProfileResponse from(PeopleProfile profile) {
        return new CardPeopleProfileResponse(
            profile.getActivityArea(),
            profile.getHashtags(),
            //TODO: 계산 프로퍼티 구현
            0,
            0
        );
    }
}
