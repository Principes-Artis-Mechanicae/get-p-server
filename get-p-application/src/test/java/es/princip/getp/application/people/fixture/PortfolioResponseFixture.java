package es.princip.getp.application.people.fixture;

import es.princip.getp.application.people.dto.response.peopleProfile.PortfolioResponse;

import java.util.List;

import static es.princip.getp.fixture.people.PortfolioFixture.portfolios;

public class PortfolioResponseFixture {

    public static List<PortfolioResponse> portfolioResponses() {
        return portfolios().stream()
            .map(portfolio -> new PortfolioResponse(
                portfolio.getDescription(),
                portfolio.getUrl().getValue()
            ))
            .toList();
    }
}
