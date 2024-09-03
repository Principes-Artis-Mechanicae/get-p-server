package es.princip.getp.api.controller.people.query.dto.people;

import es.princip.getp.api.controller.people.query.dto.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.domain.people.model.PeopleType;

public record CardPeopleResponse(
    Long peopleId,
    String nickname,
    String profileImageUri,
    PeopleType peopleType,
    long completedProjectsCount,
    long likesCount,
    CardPeopleProfileResponse profile
) {
}
