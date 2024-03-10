package es.princip.getp.fixture;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.domain.enums.MemberType;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MemberFixture {

    public static String EMAIL = "getp@princip.es";
    public static String PASSWORD = "qwer1234!";
    public static MemberType MEMBER_TYPE = MemberType.ROLE_PEOPLE;

    public static Member createMember() {
        return Member.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .memberType(MEMBER_TYPE)
            .build();
    }

    public static Member createMember(final String email) {
        return Member.builder()
            .email(email)
            .password(PASSWORD)
            .memberType(MEMBER_TYPE)
            .build();
    }

    public static Member createMember(final MemberType memberType) {
        return Member.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .memberType(memberType)
            .build();
    }

    public static Member createMember(final String email, final MemberType memberType) {
        return Member.builder()
            .email(email)
            .password(PASSWORD)
            .memberType(memberType)
            .build();
    }

    public static List<Member> createMemberList(int count) {
        return IntStream.range(0, count)
            .mapToObj(i -> Member.builder()
                .email(EMAIL + "." + i)
                .password(PASSWORD)
                .memberType(MEMBER_TYPE)
                .build())
            .collect(Collectors.toList());
    }
}
