package es.princip.getp.fixture;

import es.princip.getp.domain.member.entity.Member;
import es.princip.getp.domain.member.entity.MemberType;

public class MemberFixture {
    public static String EMAIL = "getp@princip.es";
    public static String PASSWORD = "getp@princip.es";
    public static String ROLE_TYPE = "ROLE_PEOPLE";

    public static Member createMember() {
        return Member.builder()
                        .email(EMAIL)
                        .password(PASSWORD)
                        .memberType(MemberType.valueOf(ROLE_TYPE))
                    .build();
    }
}
