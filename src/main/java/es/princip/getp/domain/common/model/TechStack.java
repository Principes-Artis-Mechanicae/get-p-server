package es.princip.getp.domain.common.model;

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
public class TechStack {

    @Column(name = "tech_stack")
    @NotBlank
    private String value;

    public TechStack(final String value) {
        this.value = value;
    }

    public static TechStack from(final String value) {
        return new TechStack(value);
    }
}
