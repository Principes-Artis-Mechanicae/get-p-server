package es.princip.getp.domain.common.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TechStack {

    @NotBlank
    private String value;

    public TechStack(final String value) {
        this.value = value;
    }

    public static TechStack from(final String value) {
        return new TechStack(value);
    }
}
