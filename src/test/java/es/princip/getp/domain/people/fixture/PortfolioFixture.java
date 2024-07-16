package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.people.command.domain.Portfolio;
import es.princip.getp.domain.people.command.presentation.dto.request.PortfolioRequest;
import es.princip.getp.domain.people.query.dto.peopleProfile.PortfolioResponse;

import java.util.List;

public class PortfolioFixture {

    public static List<Portfolio> portfolios() {
        return List.of(
            Portfolio.of("https://github.com/scv1702/1", "포트폴리오1 내용"),
            Portfolio.of("https://github.com/scv1702/2", "포트폴리오2 내용")
        );
    }

    public static List<PortfolioRequest> portfolioRequests() {
        return List.of(
            new PortfolioRequest("포트폴리오1 내용", "https://github.com/scv1702/1"),
            new PortfolioRequest("포트폴리오2 내용", "https://github.com/scv1702/2")
        );
    }

    public static List<PortfolioResponse> portfolioResponses() {
        return List.of(
            new PortfolioResponse("포트폴리오1 내용", "https://github.com/scv1702/1"),
            new PortfolioResponse("포트폴리오2 내용", "https://github.com/scv1702/2")
        );
    }
}
