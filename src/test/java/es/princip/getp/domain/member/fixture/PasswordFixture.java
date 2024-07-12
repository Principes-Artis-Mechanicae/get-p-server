package es.princip.getp.domain.member.fixture;

import es.princip.getp.domain.member.domain.model.Password;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordFixture {

    private static final PasswordEncoder encoder = new PasswordEncoder() {
        @Override
        public String encode(final CharSequence rawPassword) {
            return "";
        }

        @Override
        public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
            return false;
        }
    };

    public static final String PASSWORD = "qwer1234!";

    public static Password password() {
        return Password.of(PASSWORD);
    }
}
