package es.princip.getp.domain.member.fixture;

import es.princip.getp.domain.member.model.Email;

public class EmailFixture {
    public static final String EMAIL = "test@example.com";

    public static Email email() {
        return Email.of(EMAIL);
    }
}
