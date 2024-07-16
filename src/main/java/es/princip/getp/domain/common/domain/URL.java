package es.princip.getp.domain.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class URL {

    private static final String URL_PREFIX = "^(https|ftp|mailto|tel)://.*";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_PREFIX);

    @Column(name = "url")
    private String value;

    private URL(String url) {
        this.value = url;
    }

    private static void validate(String url) {
        if (!URL_PATTERN.matcher(url).matches()) {
            throw new IllegalArgumentException("Invalid URL: " + url);
        }
    }

    public static URL from(String url) {
        validate(url);
        return new URL(url);
    }
}
