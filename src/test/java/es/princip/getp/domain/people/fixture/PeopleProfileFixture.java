package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.people.command.domain.PeopleProfile;

import static es.princip.getp.domain.common.fixture.HashtagFixture.hashtags;
import static es.princip.getp.domain.common.fixture.TechStackFixture.techStacks;
import static es.princip.getp.domain.people.fixture.ActivityAreaFixture.activityArea;
import static es.princip.getp.domain.people.fixture.EducationFixture.education;
import static es.princip.getp.domain.people.fixture.IntroductionFixture.introduction;
import static es.princip.getp.domain.people.fixture.PortfolioFixture.portfolios;

public class PeopleProfileFixture {

    public static PeopleProfile peopleProfile() {
        return PeopleProfile.builder()
            .introduction(introduction())
            .activityArea(activityArea())
            .education(education())
            .hashtags(hashtags())
            .techStacks(techStacks())
            .portfolios(portfolios())
            .build();
    }
}
