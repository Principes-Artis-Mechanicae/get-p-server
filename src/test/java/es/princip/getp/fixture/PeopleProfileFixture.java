package es.princip.getp.fixture;

import java.util.List;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.entity.PeopleProfile;
import es.princip.getp.domain.hashtag.domain.values.Hashtag;
import es.princip.getp.domain.people.dto.request.CreatePeopleProfileRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleProfileRequest;
import es.princip.getp.domain.people.dto.response.peopleProfile.CardPeopleProfileResponse;

public class PeopleProfileFixture {

    public static String INTRODUCTION = "안녕하세요, 백엔드 개발자 겟피입니다.";
    public static String ACTIVITY_AREA = "대구광역시";
    public static String EDUCATION = "경북대학교 컴퓨터 공학";
    public static List<String> HASHTAGS_STRING = List.of("#ESFP", "#JIRA", "#DISCORD");
    public static List<String> TECHSTACKS = List.of("#Java", "#Spring", "#JPA");
    public static String UPDATED_EDUCATION = "한양대학교 컴퓨터 공학과";
    public static List<Hashtag> HASHTAGS = HASHTAGS_STRING.stream().map(Hashtag::from).toList();

    public static CreatePeopleProfileRequest createPeopleProfileRequest() {
        return new CreatePeopleProfileRequest(INTRODUCTION, ACTIVITY_AREA, TECHSTACKS, EDUCATION, HASHTAGS_STRING);
    }

    public static UpdatePeopleProfileRequest updatePeopleProfileRequest() {
        return new UpdatePeopleProfileRequest(INTRODUCTION, ACTIVITY_AREA, TECHSTACKS, UPDATED_EDUCATION, HASHTAGS_STRING);
    }

    public static PeopleProfile createPeopleProfile(People people) {
        return PeopleProfile.builder()
            .introduction(INTRODUCTION)
            .activityArea(ACTIVITY_AREA)
            .education(EDUCATION)
            .hashtags(HASHTAGS_STRING)
            .techStacks(TECHSTACKS)
            .people(people)
            .build();
    }

    public static CardPeopleProfileResponse createCardPeopleProfileResponse() {
        return new CardPeopleProfileResponse(ACTIVITY_AREA, HASHTAGS_STRING, null, null);
    }
}
