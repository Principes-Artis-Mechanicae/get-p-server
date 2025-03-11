package es.princip.getp.domain.member.model;

import es.princip.getp.domain.member.PasswordEncoder;
import es.princip.getp.domain.support.BaseModel;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Password extends BaseModel {

    public static final String PASSWORD_REGEX
        = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*_+=/])[A-Za-z\\d!@#$%^&*_+=/]{8,20}$";

    @NotNull @PasswordPattern private final String value;
    private final boolean encoded;

    public Password(final String value, final boolean encoded) {
        this.value = value;
        this.encoded = encoded;

        validate();
    }

    public static Password from(final String value) {
        return new Password(value, false);
    }

    public Password encode(final PasswordEncoder encoder) {
        return new Password(encoder.encode(value), true);
    }

    @Override
    protected void validate() {
        if (!encoded) {
            super.validate();
        }
    }

    public boolean isMatch(final PasswordEncoder encoder, final Password password) {
        if (password.encoded) {
            return encoder.matches(this.value, password.value);
        }
        return encoder.matches(this.value, encoder.encode(password.value));
    }
}
