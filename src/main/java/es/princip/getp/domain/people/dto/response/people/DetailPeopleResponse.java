package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleType;
import es.princip.getp.domain.people.dto.response.peopleProfile.DetailPeopleProfileResponse;
import lombok.Builder;

@Builder
public record DetailPeopleResponse(
    Long peopleId,
    String nickname,
    PeopleType peopleType,
    String profileImageUri,
    DetailPeopleProfileResponse profile
) {

    public static DetailPeopleResponse from(People people) {
        return DetailPeopleResponse.builder()
            .peopleId(people.getPeopleId())
            .nickname(people.getNickname())
            .peopleType(people.getPeopleType())
            .profileImageUri(people.getProfileImageUri())
            .profile(DetailPeopleProfileResponse.from(people.getProfile()))
            .build();
    }
}