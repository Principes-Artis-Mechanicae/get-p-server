package es.princip.getp.domain.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class URL {

    public static final String URL_REGEX = "^(https|ftp|mailto|tel)://.*";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    @Column(name = "url")
    private String value;

    public URL(final String value) {
        validate(value);
        this.value = value;
    }

    private static void validate(final String value) {
        Objects.requireNonNull(value);
        if (!URL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(String.format(
                "%s는 유효하지 않은 URL입니다. URL은 https|ftp|mailto|tel://로 시작해야 합니다.",
                value
            ));
        }
    }

    public static URL from(final String url) {
        return new URL(url);
    }
}
