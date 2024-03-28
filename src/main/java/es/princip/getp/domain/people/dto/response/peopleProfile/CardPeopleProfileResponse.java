package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.people.domain.entity.PeopleHashtag;
import java.util.List;

public record CardPeopleProfileResponse(
    String activityArea,
    List<String> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount
) {

    public static CardPeopleProfileResponse from(
        final String activityArea,
        final List<PeopleHashtag> hashtags
    ) {
        return new CardPeopleProfileResponse(
            activityArea,
            hashtags.stream().map(PeopleHashtag::getValue).toList(),
            //TODO: 계산 프로퍼티 구현
            0,
            0
        );
    }
}
