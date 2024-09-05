package es.princip.getp.persistence.adapter.people;

import es.princip.getp.domain.common.model.Hashtag;
import es.princip.getp.domain.common.model.TechStack;
import es.princip.getp.domain.people.model.PeopleType;
import es.princip.getp.persistence.adapter.people.model.PeopleJpaEntity;
import es.princip.getp.persistence.adapter.people.model.PeopleProfileJpaVO;
import es.princip.getp.persistence.adapter.people.model.PortfolioJpaVO;

import java.util.List;

import static es.princip.getp.fixture.common.HashtagFixture.hashtags;
import static es.princip.getp.fixture.common.TechStackFixture.techStacks;
import static es.princip.getp.fixture.member.EmailFixture.EMAIL;
import static es.princip.getp.fixture.people.ActivityAreaFixture.ACTIVITY_AREA;
import static es.princip.getp.fixture.people.EducationFixture.MAJOR;
import static es.princip.getp.fixture.people.EducationFixture.SCHOOL;
import static es.princip.getp.fixture.people.IntroductionFixture.INTRODUCTION;
import static es.princip.getp.fixture.people.PortfolioFixture.portfolios;

public class PeoplePersistenceFixture {

    private static List<String> hashtagToString() {
        return hashtags().stream()
            .map(Hashtag::getValue)
            .toList();
    }

    private static List<String> techStackToString() {
        return techStacks().stream()
            .map(TechStack::getValue)
            .toList();
    }

    private static List<PortfolioJpaVO> portfolioToJpaVO() {
        return portfolios().stream()
            .map(portfolio -> new PortfolioJpaVO(
                portfolio.getUrl().getValue(),
                portfolio.getDescription()
            ))
            .toList();
    }

    private static PeopleProfileJpaVO peopleProfileJpaVO() {
        return PeopleProfileJpaVO.builder()
            .major(MAJOR)
            .school(SCHOOL)
            .activityArea(ACTIVITY_AREA)
            .introduction(INTRODUCTION)
            .hashtags(hashtagToString())
            .techStacks(techStackToString())
            .portfolios(portfolioToJpaVO())
            .build();
    }

    public static PeopleJpaEntity peopleJpaEntity(Long memberId, PeopleType peopleType) {
        return PeopleJpaEntity.builder()
            .id(memberId)
            .email(EMAIL)
            .peopleType(peopleType)
            .memberId(memberId)
            .profile(peopleProfileJpaVO())
            .build();
    }

    public static PeopleJpaEntity peopleJpaEntityWithoutProfile(Long memberId, PeopleType peopleType) {
        return PeopleJpaEntity.builder()
            .id(memberId)
            .email(EMAIL)
            .peopleType(peopleType)
            .memberId(memberId)
            .build();
    }
}
