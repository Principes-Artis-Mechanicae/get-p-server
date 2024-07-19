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

    private Hashtag(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        return value;
    }

    public static Hashtag of(String value) {
        return new Hashtag(value);
    }
}
