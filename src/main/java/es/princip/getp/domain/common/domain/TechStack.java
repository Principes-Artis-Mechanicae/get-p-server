package es.princip.getp.domain.common.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TechStack {

    @Column(name = "tech_stack")
    @NotBlank
    private String value;

    private TechStack(String value) {
        this.value = validate(value);
    }

    private String validate(String value) {
        return value;
    }

    public static TechStack of(String value) {
        return new TechStack(value);
    }

    public static List<String> toDto(final List<TechStack> techStacks) {
        return techStacks.stream()
            .map(TechStack::getValue)
            .toList();
    }
}
