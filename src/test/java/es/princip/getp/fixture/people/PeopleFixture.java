package es.princip.getp.fixture.people;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleInfo;
import es.princip.getp.domain.people.model.PeopleProfileData;
import es.princip.getp.domain.people.model.PeopleType;

import static es.princip.getp.fixture.common.EmailFixture.email;
import static es.princip.getp.fixture.common.HashtagFixture.hashtags;
import static es.princip.getp.fixture.common.TechStackFixture.techStacks;
import static es.princip.getp.fixture.people.ActivityAreaFixture.activityArea;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.introduction;
import static es.princip.getp.fixture.people.PortfolioFixture.portfolios;

public class PeopleFixture {

    private static PeopleInfo peopleInfo(final PeopleType peopleType) {
        return new PeopleInfo(email(), peopleType);
    }

    public static People people(final MemberId memberId, final PeopleType peopleType) {
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

    public static People peopleWithoutProfile(final MemberId memberId, final PeopleType peopleType) {
        return People.of(memberId, peopleInfo(peopleType));
    }
}
