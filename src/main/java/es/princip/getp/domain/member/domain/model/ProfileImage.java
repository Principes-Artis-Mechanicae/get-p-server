package es.princip.getp.domain.member.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.net.URI;

@Embeddable
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {

    @Column(name = "profile_image_uri")
    private String uri;

    private ProfileImage(final String uri) {
        this.uri = uri;
    }

    public static ProfileImage of(final URI uri) {
        return new ProfileImage(uri.toString());
    }

    public URI getUri() {
        return URI.create(uri);
    }
}
