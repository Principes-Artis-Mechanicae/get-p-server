package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.member.fixture.EmailFixture;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleType;

public class PeopleFixture {

    public static People people(Long memberId, PeopleType peopleType) {
        return People.builder()
            .email(EmailFixture.email())
            .peopleType(peopleType)
            .memberId(memberId)
            .build();
    }
}
