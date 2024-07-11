package es.princip.getp.domain.people.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
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