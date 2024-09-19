package es.princip.getp.api.controller.people.query.dto.people;

import com.fasterxml.jackson.annotation.JsonInclude;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.DetailPeopleProfileResponse;
import es.princip.getp.domain.people.model.PeopleType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DetailPeopleResponse(
    Long peopleId,
    String nickname,
    String profileImageUri,
    PeopleType peopleType,
    long completedProjectsCount,
    long likesCount,
    Boolean liked,
    DetailPeopleProfileResponse profile
) {
}