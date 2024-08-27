package es.princip.getp.domain.member.model;

import es.princip.getp.domain.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.regex.Pattern;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Password extends BaseModel {

    public static final String PASSWORD_REGEX
        = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*_+=/])[A-Za-z\\d!@#$%^&*_+=/]{8,20}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    @NotNull private final String value;
    private final boolean encoded;

    public Password(final String value, final boolean encoded) {
        this.value = value;
        this.encoded = encoded;

        validate();
    }

    public static Password of(final String value) {
        return new Password(value, false);
    }

    public Password encode(final PasswordEncoder encoder) {
        return new Password(encoder.encode(value), true);
    }

    @Override
    public void validate() {
        super.validate();
        if (!encoded && !PASSWORD_PATTERN.matcher(value).matches()) {
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
