package es.princip.getp.fixture.people;

import es.princip.getp.domain.people.model.Portfolio;

import java.util.List;

public class PortfolioFixture {

    public static List<Portfolio> portfolios() {
        return List.of(
            Portfolio.of("포트폴리오1 내용", "https://github.com/scv1702/1"),
            Portfolio.of("포트폴리오2 내용", "https://github.com/scv1702/2")
        );
    }
}
