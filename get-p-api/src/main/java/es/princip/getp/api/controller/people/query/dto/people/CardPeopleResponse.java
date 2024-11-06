package es.princip.getp.api.controller.people.query.dto.people;

import es.princip.getp.api.controller.people.query.dto.peopleProfile.CardPeopleProfileResponse;

public record CardPeopleResponse(
    Long peopleId,
    String nickname,
    String profileImageUri,
    long completedProjectsCount,
    long likesCount,
    CardPeopleProfileResponse profile
) {
}
