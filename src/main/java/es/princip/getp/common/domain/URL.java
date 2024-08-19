package es.princip.getp.common.domain;

import es.princip.getp.common.annotation.URLPattern;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class URL {

    public static final String URL_REGEX = "^(https|ftp|mailto|tel)://.*";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    @Column(name = "url")
    @URLPattern
    @NotNull
    private String value;

    public URL(final String url) {
        validate(url);
        this.value = url;
    }

    private static void validate(final String url) {
        Objects.requireNonNull(url);
        if (!URL_PATTERN.matcher(url).matches()) {
            throw new IllegalArgumentException(String.format(
                "%s는 유효하지 않은 URL입니다. URL은 https|ftp|mailto|tel://로 시작해야 합니다.",
                url
            ));
        }
    }

    public static URL from(final String url) {
        return new URL(url);
    }
}
