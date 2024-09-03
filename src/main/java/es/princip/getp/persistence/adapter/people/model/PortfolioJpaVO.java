package es.princip.getp.persistence.adapter.people.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PortfolioJpaVO {

    @Column(name = "url")
    private String url;

    @Column(name = "description")
    private String description;
}
