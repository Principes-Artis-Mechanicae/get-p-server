package es.princip.getp.domain.people.fixture;

import es.princip.getp.domain.people.command.domain.Portfolio;
import es.princip.getp.domain.people.command.presentation.dto.request.PortfolioRequest;
import es.princip.getp.domain.people.query.dto.peopleProfile.PortfoliosResponse;

import java.util.List;

public class PortfolioFixture {

    public static List<Portfolio> portfolios() {
        return List.of(
            Portfolio.of("포트폴리오1 내용", "https://github.com/scv1702/1"),
            Portfolio.of("포트폴리오2 내용", "https://github.com/scv1702/2")
        );
    }

    public static List<PortfolioRequest> portfoliosRequest() {
        return List.of(
            new PortfolioRequest("포트폴리오1 내용", "https://github.com/scv1702/1"),
            new PortfolioRequest("포트폴리오2 내용", "https://github.com/scv1702/2")
        );
    }

    public static PortfoliosResponse portfoliosResponse() {
        return PortfoliosResponse.from(
            List.of(
                Portfolio.of("포트폴리오1 내용", "https://github.com/scv1702/1"),
                Portfolio.of("포트폴리오2 내용", "https://github.com/scv1702/2")
            )
        );
    }
}