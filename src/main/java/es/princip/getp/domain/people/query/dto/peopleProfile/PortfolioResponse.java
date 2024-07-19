package es.princip.getp.domain.people.query.dto.peopleProfile;

import es.princip.getp.domain.people.command.domain.Portfolio;

public record PortfolioResponse(
    String description,
    String url
) {

    public static PortfolioResponse from(final Portfolio portfolio) {
        return new PortfolioResponse(
            portfolio.getDescription(),
            portfolio.getUrl().getValue()
        );
    }
}