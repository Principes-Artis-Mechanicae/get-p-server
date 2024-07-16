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
public class URI {

    private static final String URI_PREFIX = "^(https|ftp|mailto|tel)://.*";
    private static final Pattern URI_PATTERN = Pattern.compile(URI_PREFIX);

    @Column(name = "uri")
    private String value;

    private URI(String uri) {
        this.value = uri;
    }

    private static void validate(String uri) {
        if (!URI_PATTERN.matcher(uri).matches()) {
            throw new IllegalArgumentException("Invalid URI: " + uri);
        }
    }

    public static URI from(String uri) {
        validate(uri);
        return new URI(uri);
    }
}
