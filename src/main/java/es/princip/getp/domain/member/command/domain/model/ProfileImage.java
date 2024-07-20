package es.princip.getp.domain.member.command.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Embeddable
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {

    @Column(name = "profile_image_uri")
    @NotNull
    private String uri;

    private ProfileImage(final String uri) {
        this.uri = uri;
    }

    public static ProfileImage of(final String uri) {
        Objects.requireNonNull(uri, "이미지 URI는 필수 입력 값입니다.");
        return new ProfileImage(uri);
    }
}
