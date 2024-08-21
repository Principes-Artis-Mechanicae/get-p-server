package es.princip.getp.fixture.people;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleInfo;
import es.princip.getp.domain.people.command.domain.PeopleProfileData;
import es.princip.getp.domain.people.command.domain.PeopleType;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.fixture.common.HashtagFixture.hashtags;
import static es.princip.getp.fixture.common.TechStackFixture.techStacks;
import static es.princip.getp.fixture.member.EmailFixture.email;

public class PeopleFixture {

    public static People people(Long memberId, PeopleType peopleType) {
        final PeopleInfo peopleInfo = new PeopleInfo(email(), peopleType);
        final People people = new People(memberId, peopleInfo);
        final PeopleProfileData data = new PeopleProfileData(
            IntroductionFixture.introduction(),
            ActivityAreaFixture.activityArea(),
            EducationFixture.education(),
            hashtags(),
            techStacks(),
            PortfolioFixture.portfolios()
        );
        people.registerProfile(data);
        return people;
    }

    public static List<People> peopleList(int size, Long memberIdBias, PeopleType peopleType) {
        return LongStream.range(0, size)
            .mapToObj(i -> people(memberIdBias + i, peopleType))
            .toList();
    }
}
