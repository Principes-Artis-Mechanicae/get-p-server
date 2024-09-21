package es.princip.getp.api.controller.people.query.dto.people;

import es.princip.getp.api.controller.people.query.dto.peopleProfile.PublicDetailPeopleProfileResponse;

public record PublicDetailPeopleResponse(
    Long peopleId,
    String nickname,
    String profileImageUri,
    long completedProjectsCount,
    long likesCount,
    PublicDetailPeopleProfileResponse profile
) {
}