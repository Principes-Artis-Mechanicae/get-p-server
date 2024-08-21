package es.princip.getp.fixture.member;

import es.princip.getp.domain.member.model.Password;

public class PasswordFixture {

    public static final String PASSWORD = "qwer1234!";

    public static Password password() {
        return Password.of(PASSWORD);
    }
}
