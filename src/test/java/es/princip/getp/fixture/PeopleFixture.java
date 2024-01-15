package es.princip.getp.fixture;

import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.people.dto.request.CreatePeopleRequestDTO;
import es.princip.getp.domain.people.entity.People;
import es.princip.getp.domain.people.entity.PeopleRoleType;

public class PeopleFixture {

    public static String NAME = "겟피";
    public static String EMAIL = "getp@princip.es";
    public static String PHONE_NUMBER = "010-1234-5678";
    public static String ROLE_TYPE = "ROLE_INDIVIDUAL";
    public static String PROFILE_IMAGE_URI = "https://he.princip.es/img.jpg";
    public static String ACCOUNT_NUMBER = "3332-112-12-12";

    public static CreatePeopleRequestDTO createPeopleRequestDTO() {
        return new CreatePeopleRequestDTO(NAME, EMAIL, PHONE_NUMBER, NAME, EMAIL, ACCOUNT_NUMBER);
    }

    public static People createPeopleByMember(Member member) {
        return People.builder()
                        .name(NAME)
                        .email(EMAIL)
                        .phoneNumber(PHONE_NUMBER)
                        .roleType(PeopleRoleType.valueOf(ROLE_TYPE))
                        .profileImageUri(PROFILE_IMAGE_URI)
                        .accountNumber(ACCOUNT_NUMBER)
                        .member(member)
                    .build();
    }
}
