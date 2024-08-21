package es.princip.getp.fixture.member;

import es.princip.getp.domain.member.model.Email;

public class EmailFixture {
    public static final String EMAIL = "test@example.com";

    public static Email email() {
        return Email.of(EMAIL);
    }
}
