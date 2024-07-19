package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.people.command.domain.PeopleProfile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static es.princip.getp.domain.common.fixture.HashtagFixture.hashtags;
import static es.princip.getp.domain.common.fixture.TechStackFixture.techStacks;
import static es.princip.getp.domain.people.fixture.ActivityAreaFixture.activityArea;
import static es.princip.getp.domain.people.fixture.EducationFixture.education;
import static es.princip.getp.domain.people.fixture.IntroductionFixture.introduction;
import static es.princip.getp.domain.people.fixture.PortfolioFixture.portfolios;

public class PeopleProfileFixture {

    public static PeopleProfile peopleProfile(final Long peopleId) {
        return PeopleProfile.builder()
            .introduction(introduction())
            .activityArea(activityArea())
            .education(education())
            .hashtags(hashtags())
            .techStacks(techStacks())
            .portfolios(portfolios())
            .peopleId(peopleId)
            .build();
    }

    public static List<PeopleProfile> peopleProfileList(final int size, final Long peopleIdBias) {
        return LongStream.range(0, size)
            .mapToObj(i -> peopleProfile(peopleIdBias + i))
            .collect(Collectors.toList());
    }
}
