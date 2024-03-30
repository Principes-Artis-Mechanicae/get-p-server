package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.enums.PeopleType;
import es.princip.getp.domain.people.dto.response.peopleProfile.PublicDetailPeopleProfileResponse;
import jakarta.validation.Valid;

public record PublicDetailPeopleResponse(
    Long peopleId,
    String nickname,
    PeopleType peopleType,
    @Valid PublicDetailPeopleProfileResponse profile
) {

    public static PublicDetailPeopleResponse from(
        People people,
        PublicDetailPeopleProfileResponse profile
    ) {
        return new PublicDetailPeopleResponse(
            people.getPeopleId(),
            people.getNickname(),
            people.getPeopleType(),
            profile
        );
    }
}