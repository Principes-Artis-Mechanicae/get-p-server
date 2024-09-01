package es.princip.getp.domain.people.model;

import es.princip.getp.domain.common.model.URL;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Portfolio {

    @Embedded
    @NotNull
    private URL url;

    @Column(name = "description")
    @NotBlank
    private String description;

    public Portfolio(final String description, final URL url) {
        this.url = url;
        this.description = description;
    }

    public static Portfolio of(final String description, final String url) {
        return new Portfolio(description, URL.from(url));
    }
}