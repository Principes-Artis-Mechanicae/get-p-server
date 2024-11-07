package es.princip.getp.fixture.people;

import es.princip.getp.domain.people.model.PeopleProfile;

import static es.princip.getp.fixture.common.HashtagFixture.hashtags;
import static es.princip.getp.fixture.common.TechStackFixture.techStacks;
import static es.princip.getp.fixture.people.EducationFixture.education;
import static es.princip.getp.fixture.people.IntroductionFixture.introduction;

public class PeopleProfileFixture {

    public static PeopleProfile peopleProfile() {
        return PeopleProfile.builder()
            .introduction(introduction())
            .activityArea(ActivityAreaFixture.activityArea())
            .education(education())
            .hashtags(hashtags())
            .techStacks(techStacks())
            .portfolios(PortfolioFixture.portfolios())
            .build();
    }
}
