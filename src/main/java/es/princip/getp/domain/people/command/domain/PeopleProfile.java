package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.common.domain.TechStack;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleProfile {

    @Column(name = "introduction")
    @NotBlank
    private String introduction;

    @Column(name = "activity_area")
    @NotBlank
    private String activityArea;

    @Embedded
    @NotNull
    private Education education;

    @ElementCollection
    @CollectionTable(name = "people_profile_hashtag", joinColumns = @JoinColumn(name = "people_id"))
    private List<Hashtag> hashtags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "people_profile_tech_stack", joinColumns = @JoinColumn(name = "people_id"))
    private List<TechStack> techStacks = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "people_profile_portfolio", joinColumns = @JoinColumn(name = "people_id"))
    private List<Portfolio> portfolios = new ArrayList<>();

    @Builder
    public PeopleProfile(
        final String introduction,
        final String activityArea,
        final Education education,
        final List<Hashtag> hashtags,
        final List<TechStack> techStacks,
        final List<Portfolio> portfolios
    ) {
        this.introduction = introduction;
        this.activityArea = activityArea;
        this.education = education;
        this.hashtags = hashtags;
        this.techStacks = techStacks;
        this.portfolios = portfolios;
    }

    public List<Hashtag> getHashtags() {
        return Collections.unmodifiableList(hashtags);
    }

    public List<TechStack> getTechStacks() {
        return Collections.unmodifiableList(techStacks);
    }

    public List<Portfolio> getPortfolios() {
        return Collections.unmodifiableList(portfolios);
    }

    public boolean isRegistered() {
        return this.introduction != null || this.activityArea != null || this.education != null;
    }
}
