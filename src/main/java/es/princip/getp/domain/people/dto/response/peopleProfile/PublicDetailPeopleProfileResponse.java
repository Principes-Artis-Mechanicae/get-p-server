package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.global.domain.values.Hashtag;
import java.util.List;

public record PublicDetailPeopleProfileResponse(
    List<Hashtag> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount
) {

    public static PublicDetailPeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new PublicDetailPeopleProfileResponse(
            peopleProfile.getHashtags(),
            //TODO: 계산 프로퍼티 구현
            0,
            0
        );
    }
}