package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.dto.response.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.domain.people.domain.enums.PeopleType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CardPeopleResponse(@NotNull Long peopleId,
                                        @NotNull String nickname,
                                        @NotNull String peopleType,
                                        @NotNull String profileImageUri,
                                        @Valid CardPeopleProfileResponse profile) {

    public static CardPeopleResponse from(final Long peopleId, final String nickname, final PeopleType peopleType,
            final String profileImageUri, final CardPeopleProfileResponse profile) {
        return new CardPeopleResponse(
            peopleId, nickname, peopleType.name(), profileImageUri, profile
        );
    }
}
