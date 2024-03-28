package es.princip.getp.global.domain.values;

import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TechStack {

    @Column(name = "tech_stack")
    private String value;

    private TechStack(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        return value;
    }

    public static TechStack from(String value) {
        return new TechStack(value);
    }
}
