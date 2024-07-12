package es.princip.getp.domain.member.fixture;

import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.member.domain.model.MemberType;

import static es.princip.getp.domain.member.fixture.EmailFixture.email;
import static es.princip.getp.domain.member.fixture.PasswordFixture.password;

public class MemberFixture {
    public static Member member(MemberType memberType) {
        return Member.of(email(), password(), memberType);
    }
}
