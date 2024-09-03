package es.princip.getp.fixture.people;

import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleInfo;
import es.princip.getp.domain.people.model.PeopleProfileData;
import es.princip.getp.domain.people.model.PeopleType;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.common.HashtagFixture.hashtags;
import static es.princip.getp.fixture.common.TechStackFixture.techStacks;
import static es.princip.getp.fixture.member.EmailFixture.email;
import static es.princip.getp.fixture.people.ActivityAreaFixture.activityArea;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.introduction;
import static es.princip.getp.fixture.people.PortfolioFixture.portfolios;

public class PeopleFixture {

    private static PeopleInfo peopleInfo(final PeopleType peopleType) {
        return new PeopleInfo(email(), peopleType);
    }

    public static People people(Long memberId, PeopleType peopleType) {
        final People people = People.of(memberId, peopleInfo(peopleType));
        final PeopleProfileData data = new PeopleProfileData(
            introduction(),
            activityArea(),
            education(),
            hashtags(),
            techStacks(),
            portfolios()
        );
        people.registerProfile(data);
        return people;
    }

    public static People peopleWithoutProfile(Long memberId, PeopleType peopleType) {
        return People.of(memberId, peopleInfo(peopleType));
    }

    public static List<People> peopleList(int size, Long memberIdBias, PeopleType peopleType) {
        return LongStream.range(0, size)
            .mapToObj(i -> people(memberIdBias + i, peopleType))
            .toList();
    }
}
