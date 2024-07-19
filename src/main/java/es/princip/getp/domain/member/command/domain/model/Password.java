package es.princip.getp.domain.member.command.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

@Embeddable
@ToString
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    public static final String PASSWORD_REGEX
        = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*_+=/])[A-Za-z\\d!@#$%^&*_+=/]{8,20}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @Column(name = "password")
    private String value;

    @Column(name = "encoded")
    private boolean encoded;

    private Password(String value, boolean encoded) {
        this.value = value;
        this.encoded = encoded;
    }

    public static Password of(final String value) {
        validate(value);
        return new Password(value, false);
    }

    public Password encode(final PasswordEncoder encoder) {
        return new Password(encoder.encode(value), true);
    }

    private static void validate(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("비밀번호는 필수 입력 값입니다.");
        }
        if (!PASSWORD_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("비밀번호 형식이 올바르지 않습니다.");
        }
    }

    public boolean isMatch(final PasswordEncoder encoder, final Password password) {
        if (password.encoded) {
            return encoder.matches(this.value, password.value);
        }
        return encoder.matches(this.value, encoder.encode(password.value));
    }
}
