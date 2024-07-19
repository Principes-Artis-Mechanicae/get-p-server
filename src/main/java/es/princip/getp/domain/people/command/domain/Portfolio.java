package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.common.domain.URL;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {

    @Embedded
    private URL url;

    @Column(name = "description")
    @NotBlank
    private String description;

    private Portfolio(URL url, String description) {
        this.url = url;
        this.description = description;
    }

    public static Portfolio of(String url, String description) {
        return new Portfolio(URL.from(url), description);
    }
}
