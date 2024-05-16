package es.princip.getp.domain.people.dto.response.people;

import es.princip.getp.domain.people.domain.entity.People;

public record CreatePeopleResponse(Long peopleId) {

    public static CreatePeopleResponse from(People people) {
        return new CreatePeopleResponse(
            people.getPeopleId()
        );
    }
}