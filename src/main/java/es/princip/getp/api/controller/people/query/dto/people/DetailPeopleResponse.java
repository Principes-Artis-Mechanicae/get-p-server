package es.princip.getp.api.controller.people.query.dto.people;

import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.people.command.domain.PeopleType;

public record DetailPeopleResponse(
    Long peopleId,
    String nickname,
    String profileImageUri,
    PeopleType peopleType,
    long completedProjectsCount,
    long likesCount,
    DetailPeopleProfileResponse profile
) {
}