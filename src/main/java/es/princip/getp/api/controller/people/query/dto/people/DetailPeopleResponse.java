package es.princip.getp.api.controller.people.query.dto.people;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DetailPeopleResponse(
    Long peopleId,
    String nickname,
    String profileImageUri,
    long completedProjectsCount,
    long likesCount,
    Boolean liked,
    DetailPeopleProfileResponse profile
) {
}