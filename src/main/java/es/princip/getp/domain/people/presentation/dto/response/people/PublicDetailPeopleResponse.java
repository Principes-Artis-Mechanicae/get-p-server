package es.princip.getp.domain.people.presentation.dto.response.people;

import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleType;
import es.princip.getp.domain.people.presentation.dto.response.peopleProfile.PublicDetailPeopleProfileResponse;
import lombok.Builder;

@Builder
public record PublicDetailPeopleResponse(
    Long peopleId,
    String nickname,
    PeopleType peopleType,
    String profileImageUri,
    PublicDetailPeopleProfileResponse profile
) {

    public static PublicDetailPeopleResponse from(People people, Member member) {
        return PublicDetailPeopleResponse.builder()
            .peopleId(people.getPeopleId())
            .nickname(member.getNickname().getValue())
            .peopleType(people.getPeopleType())
            .profileImageUri(member.getProfileImage().getUri().toString())
            .profile(PublicDetailPeopleProfileResponse.from(people.getProfile()))
            .build();
    }
}