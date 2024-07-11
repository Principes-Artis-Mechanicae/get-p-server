package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.domain.PeopleType;
import es.princip.getp.domain.people.dto.response.peopleProfile.CardPeopleProfileResponse;

public record CardPeopleResponse(
    Long peopleId,
    String nickname,
    PeopleType peopleType,
    String profileImageUri,
    CardPeopleProfileResponse profile
) {

    public static CardPeopleResponse from(
        final Long peopleId,
        final String nickname,
        final PeopleType peopleType,
        final String profileImageUri,
        final CardPeopleProfileResponse profile
    ) {
        return new CardPeopleResponse(
            peopleId,
            nickname,
            peopleType,
            profileImageUri,
            profile
        );
    }
}
