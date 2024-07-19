package es.princip.getp.domain.people.query.dto.people;

import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.people.query.dto.peopleProfile.CardPeopleProfileResponse;

public record CardPeopleResponse(
    Long peopleId,
    String nickname,
    String profileImageUri,
    PeopleType peopleType,
    Integer completedProjectsCount,
    Integer interestsCount,
    CardPeopleProfileResponse profile
) {
}
