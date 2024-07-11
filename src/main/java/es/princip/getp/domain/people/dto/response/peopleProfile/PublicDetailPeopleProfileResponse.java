package es.princip.getp.domain.people.dto.response.peopleProfile;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.people.domain.PeopleProfile;
import lombok.Builder;

import java.util.List;

@Builder
public record PublicDetailPeopleProfileResponse(
    List<Hashtag> hashtags,
    Integer completedProjectsCount,
    Integer interestsCount
) {

    public static PublicDetailPeopleProfileResponse from(final PeopleProfile peopleProfile) {
        return PublicDetailPeopleProfileResponse.builder()
            .hashtags(peopleProfile.getHashtags())
            .completedProjectsCount(0) //TODO: 완수한 프로젝트 수 계산
            .interestsCount(0) //TODO: 받은 관심 수 계산
            .build();
    }
}