package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.dto.response.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.domain.people.entity.People;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CardPeopleResponse(@NotNull Long peopleId, @NotNull String nickname, @NotNull String peopleType,
                                    @NotNull String profileImageUri, @Valid CardPeopleProfileResponse profile) {

    public static CardPeopleResponse from(People people, CardPeopleProfileResponse profile) {
        return new CardPeopleResponse(people.getPeopleId(), people.getNickname(), people.getPeopleType().name(),
            people.getProfileImageUri(), profile);
    }
}