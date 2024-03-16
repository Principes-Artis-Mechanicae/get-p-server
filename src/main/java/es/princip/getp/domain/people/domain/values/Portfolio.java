package es.princip.getp.domain.people.domain.values;

import es.princip.getp.domain.people.dto.PortfolioForm;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
    private String uri;

    @Column(name = "description")
    private String description;

    private Portfolio(String uri, String description) {
        this.uri = uri;
        this.description = description;
    }

    public static Portfolio from(PortfolioForm portfolio) {
        return new Portfolio(portfolio.uri(), portfolio.description());
    }
}
