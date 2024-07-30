package es.princip.getp.domain.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @Column(name = "hashtag")
    @NotBlank
    private String value;

    public Hashtag(final String value) {
        this.value = value;
    }

    public static Hashtag of(String value) {
        return new Hashtag(value);
    }
}
