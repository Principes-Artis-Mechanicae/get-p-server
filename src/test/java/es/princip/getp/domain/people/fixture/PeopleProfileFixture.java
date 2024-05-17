package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.people.domain.values.Education;
import es.princip.getp.domain.people.domain.values.Portfolio;
import es.princip.getp.domain.people.dto.request.CreatePeopleProfileRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleProfileRequest;
import es.princip.getp.domain.people.dto.response.peopleProfile.CardPeopleProfileResponse;
import es.princip.getp.global.domain.values.Hashtag;
import es.princip.getp.global.domain.values.TechStack;

import java.util.List;

public class PeopleProfileFixture {

    public static String INTRODUCTION = "안녕하세요, 백엔드 개발자입니다.";
    public static String ACTIVITY_AREA = "대구광역시 달서구";
    public static Education EDUCATION = Education.of("경북대학교", "컴퓨터학부");
    public static List<Hashtag> HASHTAGS = List.of(
        Hashtag.of("#ESFP"),
        Hashtag.of("#JIRA"),
        Hashtag.of("#DISCORD")
    );
    public static List<TechStack> TECHSTACKS = List.of(
        TechStack.of("Java"),
        TechStack.of("Spring"),
        TechStack.of("JPA")
    );
    public static List<Portfolio> PORTFOLIOS = List.of(
        Portfolio.of("https://github.com/Principes-Artis-Mechanicae/project1", "프로젝트1 설명"),
        Portfolio.of("https://github.com/Principes-Artis-Mechanicae/project2", "프로젝트2 설명"),
        Portfolio.of("https://github.com/Principes-Artis-Mechanicae/project3", "프로젝트3 설명")
    );

    public static CreatePeopleProfileRequest createPeopleProfileRequest() {
        return new CreatePeopleProfileRequest(
            EDUCATION,
            ACTIVITY_AREA,
            INTRODUCTION,
            TECHSTACKS,
            PORTFOLIOS,
            HASHTAGS
        );
    }

    public static UpdatePeopleProfileRequest updatePeopleProfileRequest() {
        return new UpdatePeopleProfileRequest(
            EDUCATION,
            ACTIVITY_AREA + "UPDATED",
            INTRODUCTION + "UPDATED",
            TECHSTACKS,
            PORTFOLIOS,
            HASHTAGS
        );
    }

    public static PeopleProfile createPeopleProfile(People people) {
        return PeopleProfile.builder()
            .introduction(INTRODUCTION)
            .activityArea(ACTIVITY_AREA)
            .education(EDUCATION)
            .hashtags(HASHTAGS)
            .techStacks(TECHSTACKS)
            .portfolios(PORTFOLIOS)
            .people(people)
            .build();
    }

    public static CardPeopleProfileResponse createCardPeopleProfileResponse() {
        return new CardPeopleProfileResponse(
            ACTIVITY_AREA,
            HASHTAGS,
            null,
            null);
    }
}
