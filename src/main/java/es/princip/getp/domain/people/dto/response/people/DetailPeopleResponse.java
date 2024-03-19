package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.dto.response.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.people.domain.entity.People;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DetailPeopleResponse(@NotNull Long peopleId, @NotNull String nickname, @NotNull String peopleType,
                                    @NotNull String profileImageUri, @Valid DetailPeopleProfileResponse profile) {

    public static DetailPeopleResponse from(People people, DetailPeopleProfileResponse profile) {
        return new DetailPeopleResponse(people.getPeopleId(), people.getNickname(), people.getPeopleType().name(),
        people.getProfileImageUri(), profile);
    }
}