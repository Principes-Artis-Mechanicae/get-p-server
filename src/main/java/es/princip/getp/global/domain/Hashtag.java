package es.princip.getp.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
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
