package es.princip.getp.fixture.common;

import es.princip.getp.domain.common.model.Email;

public class EmailFixture {
    public static final String EMAIL = "test@example.com";

    public static Email email() {
        return Email.of(EMAIL);
    }
}
