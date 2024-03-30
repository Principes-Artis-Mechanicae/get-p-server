package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.enums.PeopleType;
import es.princip.getp.domain.people.dto.response.peopleProfile.DetailPeopleProfileResponse;

public record DetailPeopleResponse(
    Long peopleId,
    String nickname,
    PeopleType peopleType,
    String profileImageUri,
    DetailPeopleProfileResponse profile
) {

    public static DetailPeopleResponse from(People people, DetailPeopleProfileResponse profile) {
        return new DetailPeopleResponse(
            people.getPeopleId(),
            people.getNickname(),
            people.getPeopleType(),
            people.getProfileImageUri(),
            profile
        );
    }
}