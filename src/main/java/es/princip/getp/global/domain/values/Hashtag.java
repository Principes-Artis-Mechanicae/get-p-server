package es.princip.getp.global.domain.values;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @Column(name = "hashtag")
    private String value;

    private Hashtag(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        return value;
    }

    public static Hashtag from(String value) {
        return new Hashtag(value);
    }
}
