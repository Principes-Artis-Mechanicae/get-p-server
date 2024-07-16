package es.princip.getp.domain.member.fixture;

import es.princip.getp.domain.member.domain.model.Password;

public class PasswordFixture {

    public static final String PASSWORD = "qwer1234!";

    public static Password password() {
        return Password.of(PASSWORD);
    }
}
