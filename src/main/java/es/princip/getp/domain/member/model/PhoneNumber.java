package es.princip.getp.domain.member.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@ToString
@EqualsAndHashCode
public class PhoneNumber {

    public static final String PHONE_REGEX = "^[0-9]+$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    private final String value;

    public PhoneNumber(final String value) {
        validate(value);
        this.value = value;
    }

    public static PhoneNumber of(final String value) {
        return new PhoneNumber(value);
    }

    private static void validate(final String value) {
        Objects.requireNonNull(value);
        if (!PHONE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다.");
        }
    }
}
