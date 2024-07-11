package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleType;
import es.princip.getp.domain.people.dto.response.peopleProfile.PublicDetailPeopleProfileResponse;
import lombok.Builder;

@Builder
public record PublicDetailPeopleResponse(
    Long peopleId,
    String nickname,
    PeopleType peopleType,
    String profileImageUri,
    PublicDetailPeopleProfileResponse profile
) {

    public static PublicDetailPeopleResponse from(People people) {
        return PublicDetailPeopleResponse.builder()
            .peopleId(people.getPeopleId())
            .nickname(people.getNickname())
            .peopleType(people.getPeopleType())
            .profileImageUri(people.getProfileImageUri())
            .profile(PublicDetailPeopleProfileResponse.from(people.getProfile()))
            .build();
    }
}