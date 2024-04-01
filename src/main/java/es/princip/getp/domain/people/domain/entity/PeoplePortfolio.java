package es.princip.getp.domain.people.domain.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.people.domain.values.Portfolio;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "people_portfolio")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeoplePortfolio extends BaseTimeEntity {

    @Id
    @Column(name = "people_portfolio_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "people_profile_id")
    private PeopleProfile peopleProfile;

    @Embedded
    private Portfolio portfolio;

    private PeoplePortfolio(PeopleProfile peopleProfile, Portfolio portfolio) {
        this.peopleProfile = peopleProfile;
        this.portfolio = portfolio;
    }

    public static PeoplePortfolio of(PeopleProfile peopleProfile, Portfolio portfolio) {
        return new PeoplePortfolio(peopleProfile, portfolio);
    }
}