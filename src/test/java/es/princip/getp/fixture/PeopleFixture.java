package es.princip.getp.fixture;

import java.util.ArrayList;
import java.util.List;
import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.entity.PeopleType;

public class PeopleFixture {

    public static String NAME = "겟피";
    public static String EMAIL = "getp@princip.es";
    public static String PHONE_NUMBER = "010-1234-5678";
    public static String ROLE_TYPE = "ROLE_INDIVIDUAL";
    public static String PROFILE_IMAGE_URI = "https://he.princip.es/img.jpg";
    public static String ACCOUNT_NUMBER = "3332-112-12-12";
    public static String UPDATED_NAME = "GET-P";

    public static CreatePeopleRequest createPeopleRequest() {
        return new CreatePeopleRequest(NAME, EMAIL, PHONE_NUMBER, ROLE_TYPE, PROFILE_IMAGE_URI,
            ACCOUNT_NUMBER);
    }

    public static UpdatePeopleRequest updatePeopleRequest() {
        return new UpdatePeopleRequest(UPDATED_NAME, EMAIL, PHONE_NUMBER, ROLE_TYPE,
            PROFILE_IMAGE_URI, ACCOUNT_NUMBER);
    }

    public static People createPeopleByMember(Member member) {
        return People.builder()
            .nickname(NAME)
            .email(EMAIL)
            .phoneNumber(PHONE_NUMBER)
            .peopleType(PeopleType.valueOf(ROLE_TYPE))
            .profileImageUri(PROFILE_IMAGE_URI)
            .accountNumber(ACCOUNT_NUMBER)
            .member(member)
            .build();
    }

    public static List<People> createPeopleListByMember(List<Member> memberList, int count) {
        List<People> peopleList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            People people = People.builder()
                .nickname(NAME + i)
                .email(EMAIL)
                .phoneNumber(PHONE_NUMBER)
                .peopleType(PeopleType.valueOf(ROLE_TYPE))
                .profileImageUri(PROFILE_IMAGE_URI)
                .accountNumber(ACCOUNT_NUMBER)
                .member(memberList.get(i))
                .build();
            peopleList.add(people);
        }
        return peopleList;
    }
}
