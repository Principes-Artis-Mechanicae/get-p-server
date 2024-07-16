package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.common.domain.TechStack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleProfile extends BaseTimeEntity {

    @Id
    @Column(name = "people_profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "activity_area")
    private String activityArea;

    @Embedded
    private Education education;

    @ElementCollection
    @CollectionTable(name = "people_profile_hashtag", joinColumns = @JoinColumn(name = "people_profile_id"))
    @Builder.Default
    private List<Hashtag> hashtags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "people_profile_tech_stack", joinColumns = @JoinColumn(name = "people_profile_id"))
    @Builder.Default
    private List<TechStack> techStacks = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "people_profile_portfolio", joinColumns = @JoinColumn(name = "people_profile_id"))
    @Builder.Default
    private List<Portfolio> portfolios = new ArrayList<>();

    @Column(name = "people_id")
    private Long peopleId;

    private void setIntroduction(final String introduction) {
        this.introduction = introduction;
    }

    private void setActivityArea(final String activityArea) {
        if (activityArea == null) {
            throw new IllegalArgumentException("activityArea must not be null");
        }
        this.activityArea = activityArea;
    }

    private void setEducation(final Education education) {
        if (education == null) {
            throw new IllegalArgumentException("education must not be null");
        }
        this.education = education;
    }

    private void setHashtags(final List<Hashtag> hashtags) {
        if (hashtags == null) {
            throw new IllegalArgumentException("hashtags must not be null");
        }
        this.hashtags.clear();
        this.hashtags.addAll(hashtags);
    }

    private void setTechStacks(final List<TechStack> techStacks) {
        if (techStacks == null) {
            throw new IllegalArgumentException("techStacks must not be null");
        }
        this.techStacks.clear();
        this.techStacks.addAll(techStacks);
    }

    private void setPortfolios(final List<Portfolio> portfolios) {
        if (portfolios == null) {
            throw new IllegalArgumentException("portfolios must not be null");
        }
        this.portfolios.clear();
        this.portfolios.addAll(portfolios);
    }

    public void edit(
        final Long peopleId,
        final String introduction,
        final String activityArea,
        final Education education,
        final List<Hashtag> hashtags,
        final List<TechStack> techStacks,
        final List<Portfolio> portfolios
    ) {
        if (!this.peopleId.equals(peopleId)) {
            throw new IllegalArgumentException("peopleId must be same");
        }
        setIntroduction(introduction);
        setActivityArea(activityArea);
        setEducation(education);
        setHashtags(hashtags);
        setTechStacks(techStacks);
        setPortfolios(portfolios);
    }
}
