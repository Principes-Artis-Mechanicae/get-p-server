package es.princip.getp.domain.member.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {

    @Column(name = "profile_image_uri")
    private String uri;

    private ProfileImage(final String uri) {
        this.uri = uri;
    }

    public static ProfileImage of(final String uri) {
        return new ProfileImage(uri);
    }
}
