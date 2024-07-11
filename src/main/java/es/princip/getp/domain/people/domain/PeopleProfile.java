package es.princip.getp.domain.people.domain;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.people.dto.request.UpdatePeopleProfileRequest;
import es.princip.getp.domain.project.domain.TechStack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "people_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_profile_id")
    private Long id;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "activity_area")
    private String activityArea;

    @Embedded
    private Education education;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "people_id")
    private People people;
    
    @OneToMany(mappedBy = "peopleProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PeopleHashtag> hashtags = new ArrayList<>();

    @OneToMany(mappedBy = "peopleProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PeopleTechStack> techStacks = new ArrayList<>();

    @OneToMany(mappedBy = "peopleProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PeoplePortfolio> portfolios = new ArrayList<>();

    public List<Hashtag> getHashtags() {
        return hashtags.stream()
            .map(PeopleHashtag::getHashtag)
            .toList();
    }

    public List<TechStack> getTechStacks() {
        return techStacks.stream()
            .map(PeopleTechStack::getTechStack)
            .toList();
    }

    public List<Portfolio> getPortfolios() {
        return portfolios.stream()
            .map(PeoplePortfolio::getPortfolio)
            .toList();
    }

    @Builder
    public PeopleProfile(
        final String introduction,
        final String activityArea,
        final Education education,
        final List<Hashtag> hashtags,
        final List<TechStack> techStacks,
        final List<Portfolio> portfolios,
        final People people
    ) {
        this.introduction = introduction;
        this.activityArea = activityArea;
        this.education = education;
        this.hashtags.addAll(hashtags.stream()
            .map(hashtag -> PeopleHashtag.of(this, hashtag))
            .toList());
        this.techStacks.addAll(techStacks.stream()
            .map(techStack -> PeopleTechStack.of(this, techStack))
            .toList());
        this.portfolios.addAll(portfolios.stream()
            .map(portfolio -> PeoplePortfolio.of(this, portfolio))
            .toList());
        this.people = people;
    }

    public void update(final UpdatePeopleProfileRequest request) {
        this.introduction = request.introduction();
        this.activityArea = request.activityArea();
        this.education = request.education();
        this.hashtags.clear();
        this.hashtags.addAll(request.hashtags().stream()
            .map(hashtag -> PeopleHashtag.of(this, hashtag))
            .toList());
        this.techStacks.clear();
        this.techStacks.addAll(request.techStacks().stream()
            .map(techStack -> PeopleTechStack.of(this, techStack))
            .toList());
        this.portfolios.clear();
        this.portfolios.addAll(request.portfolios().stream()
            .map(portfolio -> PeoplePortfolio.of(this, portfolio))
            .toList());
    }
}
