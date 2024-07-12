package es.princip.getp.domain.member.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

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
    private String value;

    private PhoneNumber(String value) {
        this.value = value;
    }

    public static PhoneNumber of(String value) {
        validate(value);
        return new PhoneNumber(value);
    }

    private static void validate(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("전화번호는 필수 입력 값입니다.");
        }
        if (!PHONE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("전화번호 형식이 올바르지 않습니다.");
        }
    }
}
