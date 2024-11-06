package es.princip.getp.fixture.member;

import es.princip.getp.domain.member.model.Member;
import es.princip.getp.domain.member.model.MemberType;

import static es.princip.getp.fixture.common.EmailFixture.email;

public class MemberFixture {

    public static Member member(final MemberType memberType) {
        return Member.of(email(), PasswordFixture.password(), memberType);
    }
}
