package es.princip.getp.domain.people.dto;

import es.princip.getp.domain.people.domain.values.Portfolio;
import es.princip.getp.global.validator.annotation.Hyperlink;
import jakarta.validation.constraints.NotBlank;

public record PortfolioForm(@Hyperlink @NotBlank String uri, @NotBlank String description) {
    public static PortfolioForm from(Portfolio portfolio) {
        return new PortfolioForm(portfolio.getUri(), portfolio.getDescription());
    }
}