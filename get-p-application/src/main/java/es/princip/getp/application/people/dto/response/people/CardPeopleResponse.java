package es.princip.getp.application.people.dto.response.people;

import es.princip.getp.application.people.dto.response.peopleProfile.CardPeopleProfileResponse;

public record CardPeopleResponse(
    Long peopleId,
    String nickname,
    String profileImageUri,
    long completedProjectsCount,
    long likesCount,
    CardPeopleProfileResponse profile
) {
}
