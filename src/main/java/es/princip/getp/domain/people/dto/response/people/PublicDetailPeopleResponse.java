package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.dto.response.peopleProfile.PublicDetailPeopleProfileResponse;
import es.princip.getp.domain.people.domain.entity.People;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record PublicDetailPeopleResponse(@NotNull Long peopleId, @NotNull String nickname, @NotNull String peopleType,
                                     @Valid PublicDetailPeopleProfileResponse profile) {

    public static PublicDetailPeopleResponse from(People people, PublicDetailPeopleProfileResponse profile) {
        return new PublicDetailPeopleResponse(people.getPeopleId(), people.getNickname(), people.getPeopleType().name(),
         profile);
    }
}