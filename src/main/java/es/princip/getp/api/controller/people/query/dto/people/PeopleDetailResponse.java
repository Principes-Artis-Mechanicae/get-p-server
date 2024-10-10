package es.princip.getp.api.controller.people.query.dto.people;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PeopleProfileDetailResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PeopleDetailResponse(
    Long peopleId,
    String nickname,
    String profileImageUri,
    long completedProjectsCount,
    long likesCount,
    Boolean liked,
    PeopleProfileDetailResponse profile
) {
}