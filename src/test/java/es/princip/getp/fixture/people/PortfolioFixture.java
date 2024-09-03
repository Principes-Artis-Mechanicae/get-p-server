package es.princip.getp.fixture.people;

import es.princip.getp.api.controller.people.command.dto.request.PortfolioRequest;
import es.princip.getp.api.controller.people.query.dto.peopleProfile.PortfolioResponse;
import es.princip.getp.domain.people.model.Portfolio;

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

    public static List<PortfolioResponse> portfoliosResponse() {
        return List.of(
            new PortfolioResponse("포트폴리오1 내용", "https://github.com/scv1702/1"),
            new PortfolioResponse("포트폴리오2 내용", "https://github.com/scv1702/2")
        );
    }
}
