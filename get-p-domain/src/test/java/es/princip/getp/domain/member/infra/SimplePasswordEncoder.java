package es.princip.getp.domain.member.infra;


import es.princip.getp.domain.member.PasswordEncoder;

public class SimplePasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(final CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        return rawPassword.equals(encodedPassword);
    }
}
