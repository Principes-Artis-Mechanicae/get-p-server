package es.princip.getp.domain.people.model;

import es.princip.getp.domain.common.model.URL;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Portfolio {

    @NotNull private URL url;
    @NotBlank private String description;

    public Portfolio(final String description, final URL url) {
        this.url = url;
        this.description = description;
    }

    public static Portfolio of(final String description, final String url) {
        return new Portfolio(description, URL.from(url));
    }
}
