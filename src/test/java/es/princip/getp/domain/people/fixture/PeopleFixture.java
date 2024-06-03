package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.people.domain.enums.PeopleType;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.dto.response.people.CardPeopleResponse;

import java.util.List;
import java.util.stream.LongStream;

public class PeopleFixture {

    public static String NICKNAME = "knu12370";
    public static String EMAIL = "getp@princip.es";
    public static String PHONE_NUMBER = "01012345678";
    public static PeopleType PEOPLE_TYPE = PeopleType.INDIVIDUAL;
    public static String PROFILE_IMAGE_URI = "/images/1/profile/image.jpeg";
    public static String UPDATED_NICKNAME = "scv1702";

    public static CreatePeopleRequest createPeopleRequest() {
        return new CreatePeopleRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            PEOPLE_TYPE,
            PROFILE_IMAGE_URI
        );
    }

    public static UpdatePeopleRequest updatePeopleRequest() {
        return new UpdatePeopleRequest(
            UPDATED_NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            PEOPLE_TYPE,
            PROFILE_IMAGE_URI
        );
    }

    public static People createPeople(Member member) {
        return People.from(member, createPeopleRequest());
    }

    public static List<People> createPeopleList(List<Member> memberList) {
        return memberList.stream().map(member -> People.builder()
            .email(EMAIL)
            .peopleType(PEOPLE_TYPE)
            .member(member)
            .build()).toList();
    }

    public static List<CardPeopleResponse> createCardPeopleResponses(int count) {
        return LongStream.range(0, count)
            .mapToObj(i -> CardPeopleResponse.from(
                i,
                NICKNAME,
                PEOPLE_TYPE,
                PROFILE_IMAGE_URI,
                PeopleProfileFixture.createCardPeopleProfileResponse()))
            .toList();
    }
}
