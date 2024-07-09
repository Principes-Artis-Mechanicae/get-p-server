package es.princip.getp.domain.member.fixture;

import es.princip.getp.domain.member.domain.entity.Member;
import es.princip.getp.domain.member.domain.enums.MemberType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MemberFixture {

    public static final String EMAIL = "getp@princip.es";
    public static final String PASSWORD = "qwer1234!";
    public static final String NICKNAME = "scv1702";
    public static final String PHONE_NUMBER = "01012345678";
    public static final String PROFILE_IMAGE_URI = "/images/1/profile/image.jpg";
    public static final MemberType MEMBER_TYPE = MemberType.ROLE_PEOPLE;

    public static Member createMember() {
        return Member.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .memberType(MEMBER_TYPE)
            .nickname(NICKNAME)
            .phoneNumber(PHONE_NUMBER)
            .profileImageUri(PROFILE_IMAGE_URI)
            .build();
    }

    public static Member createMember(final String email) {
        return Member.builder()
            .email(email)
            .password(PASSWORD)
            .memberType(MEMBER_TYPE)
            .nickname(NICKNAME)
            .phoneNumber(PHONE_NUMBER)
            .profileImageUri(PROFILE_IMAGE_URI)
            .build();
    }

    public static Member createMember(final MemberType memberType) {
        return Member.builder()
            .email(EMAIL)
            .password(PASSWORD)
            .memberType(memberType)
            .nickname(NICKNAME)
            .phoneNumber(PHONE_NUMBER)
            .profileImageUri(PROFILE_IMAGE_URI)
            .build();
    }

    public static Member createMember(final String email, final MemberType memberType) {
        return Member.builder()
            .email(email)
            .password(PASSWORD)
            .memberType(memberType)
            .nickname(NICKNAME)
            .phoneNumber(PHONE_NUMBER)
            .profileImageUri(PROFILE_IMAGE_URI)
            .build();
    }

    public static Member createMember(
        final String nickname,
        final String phoneNumber
    ) {
        return Member.builder()
            .password(PASSWORD)
            .memberType(MEMBER_TYPE)
            .nickname(nickname)
            .phoneNumber(phoneNumber)
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
