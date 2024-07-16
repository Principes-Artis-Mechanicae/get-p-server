package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.project.annotation.Hyperlink;
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
public class Portfolio {

    @Column(name = "uri")
    @Hyperlink @NotBlank
    private String uri;

    @Column(name = "description")
    @NotBlank
    private String description;

    private Portfolio(String uri, String description) {
        this.uri = uri;
        this.description = description;
    }

    public static Portfolio of(String uri, String description) {
        return new Portfolio(uri, description);
    }
}
