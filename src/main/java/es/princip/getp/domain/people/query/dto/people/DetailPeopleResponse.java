package es.princip.getp.domain.people.query.dto.people;

import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.people.query.dto.peopleProfile.DetailPeopleProfileResponse;

public record DetailPeopleResponse(
    Long peopleId,
    PeopleType peopleType,
    PeopleMemberResponse member,
    DetailPeopleProfileResponse profile
) {
}