package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleInfo;
import es.princip.getp.domain.people.command.domain.PeopleProfileData;
import es.princip.getp.domain.people.command.domain.PeopleType;

import java.util.List;
import java.util.stream.LongStream;

import static es.princip.getp.domain.common.fixture.HashtagFixture.hashtags;
import static es.princip.getp.domain.common.fixture.TechStackFixture.techStacks;
import static es.princip.getp.domain.member.fixture.EmailFixture.email;
import static es.princip.getp.domain.people.fixture.ActivityAreaFixture.activityArea;
import static es.princip.getp.domain.people.fixture.EducationFixture.education;
import static es.princip.getp.domain.people.fixture.IntroductionFixture.introduction;
import static es.princip.getp.domain.people.fixture.PortfolioFixture.portfolios;

public class PeopleFixture {

    public static People people(Long memberId, PeopleType peopleType) {
        final PeopleInfo peopleInfo = new PeopleInfo(email(), peopleType);
        final People people = new People(memberId, peopleInfo);
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

    public static List<People> peopleList(int size, Long memberIdBias, PeopleType peopleType) {
        return LongStream.range(0, size)
            .mapToObj(i -> people(memberIdBias + i, peopleType))
            .toList();
    }
}
