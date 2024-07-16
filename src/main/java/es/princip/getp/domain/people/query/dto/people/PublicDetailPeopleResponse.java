package es.princip.getp.domain.people.query.dto.people;

import es.princip.getp.domain.people.command.domain.PeopleType;
import es.princip.getp.domain.people.query.dto.peopleProfile.PublicDetailPeopleProfileResponse;

public record PublicDetailPeopleResponse(
    Long peopleId,
    PeopleType peopleType,
    PeopleMemberResponse member,
    PublicDetailPeopleProfileResponse profile
) {
}