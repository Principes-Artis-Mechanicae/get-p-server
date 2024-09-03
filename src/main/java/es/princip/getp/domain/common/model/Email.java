package es.princip.getp.domain.common.model;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
public class Email extends BaseModel {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @NotNull private final String value;

    public Email(final String value) {
        this.value = value;
        validate();
    }

    public static Email of(final String value) {
        return new Email(value);
    }

    public boolean isMatch(final Email email) {
        return this.value.equals(email.value);
    }
}
