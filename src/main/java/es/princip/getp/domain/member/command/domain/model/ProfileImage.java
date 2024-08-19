package es.princip.getp.domain.member.command.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode
public class ProfileImage {

    private final String url;

    public ProfileImage(final String url) {
        this.url = url;
    }

    public static ProfileImage of(final String url) {
        validate(url);
        return new ProfileImage(url);
    }

    private static void validate(final String url) {
        Objects.requireNonNull(url);
    }
}
