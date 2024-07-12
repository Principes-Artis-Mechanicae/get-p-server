package es.princip.getp.domain.people.presentation.dto.response.people;

import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleType;
import es.princip.getp.domain.people.presentation.dto.response.peopleProfile.DetailPeopleProfileResponse;
import lombok.Builder;

@Builder
public record DetailPeopleResponse(
    Long peopleId,
    String nickname,
    PeopleType peopleType,
    String profileImageUri,
    DetailPeopleProfileResponse profile
) {

    public static DetailPeopleResponse from(People people, Member member) {
        return DetailPeopleResponse.builder()
            .peopleId(people.getPeopleId())
            .nickname(member.getNickname().getValue())
            .peopleType(people.getPeopleType())
            .profileImageUri(member.getProfileImage().getUri().toString())
            .profile(DetailPeopleProfileResponse.from(people.getProfile()))
            .build();
    }
}