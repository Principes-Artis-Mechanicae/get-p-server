package es.princip.getp.domain.common.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @NotBlank
    private String value;

    public Hashtag(final String value) {
        this.value = value;
    }

    public static Hashtag from(String value) {
        return new Hashtag(value);
    }
}
