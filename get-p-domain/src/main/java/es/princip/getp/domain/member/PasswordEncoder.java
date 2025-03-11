package es.princip.getp.domain.member;

public interface PasswordEncoder {

    String encode(final CharSequence rawPassword);

    boolean matches(final CharSequence rawPassword, final String encodedPassword);
}
