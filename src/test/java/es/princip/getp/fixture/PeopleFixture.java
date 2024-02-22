package es.princip.getp.fixture;

import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequest;
import es.princip.getp.domain.people.dto.request.UpdatePeopleRequest;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.entity.PeopleType;
import java.util.List;

public class PeopleFixture {

    public static String NICKNAME = "knu12370";
    public static String EMAIL = "getp@princip.es";
    public static String PHONE_NUMBER = "010-1234-5678";
    public static PeopleType PEOPLE_TYPE = PeopleType.ROLE_INDIVIDUAL;
    public static String PROFILE_IMAGE_URI = "https://example.com/img.jpg";
    public static String ACCOUNT_NUMBER = "3332-112-12-12";
    public static String UPDATED_NICKNAME = "scv1702";

    public static CreatePeopleRequest createPeopleRequest() {
        return new CreatePeopleRequest(
            NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            PEOPLE_TYPE.name(),
            PROFILE_IMAGE_URI,
            ACCOUNT_NUMBER);
    }

    public static UpdatePeopleRequest updatePeopleRequest() {
        return new UpdatePeopleRequest(
            UPDATED_NICKNAME,
            EMAIL,
            PHONE_NUMBER,
            PEOPLE_TYPE.name(),
            PROFILE_IMAGE_URI,
            ACCOUNT_NUMBER);
    }

    public static People createPeople(Member member) {
        return People.builder()
            .nickname(NICKNAME)
            .email(EMAIL)
            .phoneNumber(PHONE_NUMBER)
            .peopleType(PEOPLE_TYPE)
            .profileImageUri(PROFILE_IMAGE_URI)
            .accountNumber(ACCOUNT_NUMBER)
            .member(member)
            .build();
    }

    public static List<People> createPeopleList(List<Member> memberList) {
        return memberList.stream().map(member -> People.builder()
            .nickname(NICKNAME)
            .email(EMAIL)
            .phoneNumber(PHONE_NUMBER)
            .peopleType(PEOPLE_TYPE)
            .profileImageUri(PROFILE_IMAGE_URI)
            .accountNumber(ACCOUNT_NUMBER)
            .member(member)
            .build()).toList();
    }
}
