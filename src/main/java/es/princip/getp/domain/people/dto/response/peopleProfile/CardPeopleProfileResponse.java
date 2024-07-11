package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.hashtag.domain.Hashtag;

import java.util.List;

public record CardPeopleProfileResponse(
    String activityArea,
    List<Hashtag> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount
) {

    public static CardPeopleProfileResponse from(
        final String activityArea,
        final List<Hashtag> hashtags
    ) {
        return new CardPeopleProfileResponse(
            activityArea,
            hashtags,
            //TODO: 계산 프로퍼티 구현
            0,
            0
        );
    }
}
