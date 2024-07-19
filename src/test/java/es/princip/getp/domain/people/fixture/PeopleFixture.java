package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.member.fixture.EmailFixture;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleType;

import java.util.List;
import java.util.stream.LongStream;

public class PeopleFixture {

    public static People people(Long memberId, PeopleType peopleType) {
        return People.builder()
            .email(EmailFixture.email())
            .peopleType(peopleType)
            .memberId(memberId)
            .build();
    }

    public static List<People> peopleList(int size, Long memberIdBias, PeopleType peopleType) {
        return LongStream.range(0, size)
            .mapToObj(i -> people(memberIdBias + i, peopleType))
            .toList();
    }
}
