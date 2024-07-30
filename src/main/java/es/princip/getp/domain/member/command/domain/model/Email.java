package es.princip.getp.domain.member.command.domain.model;

import es.princip.getp.domain.member.command.annotation.EmailPattern;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Email {
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Column(name = "email")
    @NotNull
    @EmailPattern
    private String value;

    private Email(final String value) {
        this.value = value;
    }

    public static Email of(final String value) {
        validate(value);
        return new Email(value);
    }

    private static void validate(final String value) {
        Objects.requireNonNull(value);
        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    public boolean isMatch(final Email email) {
        return this.value.equals(email.value);
    }
}
