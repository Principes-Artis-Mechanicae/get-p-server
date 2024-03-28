package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.people.domain.entity.PeopleHashtag;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import java.util.List;

public record PublicDetailPeopleProfileResponse(
    List<String> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount
) {

    public static PublicDetailPeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return new PublicDetailPeopleProfileResponse(
            peopleProfile.getHashtags().stream().map(PeopleHashtag::getValue).toList(),
            //TODO: 계산 프로퍼티 구현
            0,
            0
        );
    }
}