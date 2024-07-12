package es.princip.getp.domain.people.presentation.dto.response.people;

import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.domain.PeopleType;
import es.princip.getp.domain.people.presentation.dto.response.peopleProfile.CardPeopleProfileResponse;

public record CardPeopleResponse(
    Long peopleId,
    String nickname,
    PeopleType peopleType,
    String profileImageUri,
    CardPeopleProfileResponse profile
) {

    public static CardPeopleResponse from(final People people, final Member member) {
        return new CardPeopleResponse(
            people.getPeopleId(),
            member.getNickname().getValue(),
            people.getPeopleType(),
            member.getProfileImage().getUri().toString(),
            CardPeopleProfileResponse.from(people.getProfile())
        );
    }
}
