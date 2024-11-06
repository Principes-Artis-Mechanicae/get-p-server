package es.princip.getp.domain.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @Column(name = "hashtag")
    @NotBlank
    private String value;

    public Hashtag(final String value) {
        this.value = value;
    }

    public static Hashtag from(String value) {
        return new Hashtag(value);
    }
}
