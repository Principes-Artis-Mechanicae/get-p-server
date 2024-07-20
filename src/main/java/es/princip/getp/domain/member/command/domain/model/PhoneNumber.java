package es.princip.getp.domain.member.command.domain.model;

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
public class PhoneNumber {

    public static final String PHONE_REGEX = "^[0-9]+$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    @Column(name = "phone_number")
    @NotNull
    private String value;

    private PhoneNumber(final String value) {
        this.value = value;
    }

    public static PhoneNumber of(final String value) {
        validate(value);
        return new PhoneNumber(value);
    }

    private static void validate(final String value) {
        Objects.requireNonNull(value, "전화번호는 필수 입력 값입니다.");
        if (!PHONE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다.");
        }
    }
}
