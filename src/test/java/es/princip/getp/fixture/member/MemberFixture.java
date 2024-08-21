package es.princip.getp.fixture.member;

import es.princip.getp.domain.member.model.Email;
import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberType;

import java.util.List;
import java.util.stream.IntStream;

import static es.princip.getp.fixture.member.EmailFixture.email;

public class MemberFixture {

    public static Member member(final MemberType memberType) {
        return Member.of(email(), PasswordFixture.password(), memberType);
    }

    public static List<Member> memberList(final int size, final int bias, final MemberType memberType) {
        return IntStream.range(bias, bias + size)
            .mapToObj(i -> Member.of(Email.of("test" + i + "@example.com"), PasswordFixture.password(), memberType))
            .toList();
    }
}
