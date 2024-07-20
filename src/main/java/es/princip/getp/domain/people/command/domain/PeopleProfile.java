package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
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
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PeopleProfile extends BaseTimeEntity {

    @Id
    @Column(name = "people_profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

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
    @CollectionTable(name = "people_profile_hashtag", joinColumns = @JoinColumn(name = "people_profile_id"))
    private List<Hashtag> hashtags = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "people_profile_tech_stack", joinColumns = @JoinColumn(name = "people_profile_id"))
    private List<TechStack> techStacks = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "people_profile_portfolio", joinColumns = @JoinColumn(name = "people_profile_id"))
    private List<Portfolio> portfolios = new ArrayList<>();

    @Column(name = "people_id", nullable = false, unique = true)
    @NotNull
    private Long peopleId;

    @Builder
    public PeopleProfile(
        final String introduction,
        final String activityArea,
        final Education education,
        final List<Hashtag> hashtags,
        final List<TechStack> techStacks,
        final List<Portfolio> portfolios,
        final Long peopleId
    ) {
        setIntroduction(introduction);
        setActivityArea(activityArea);
        setEducation(education);
        setHashtags(hashtags);
        setTechStacks(techStacks);
        setPortfolios(portfolios);
        this.peopleId = peopleId;
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

    private void setIntroduction(final String introduction) {
        Objects.requireNonNull(introduction);
        this.introduction = introduction;
    }

    private void setActivityArea(final String activityArea) {
        Objects.requireNonNull(activityArea);
        this.activityArea = activityArea;
    }

    private void setEducation(final Education education) {
        Objects.requireNonNull(education);
        this.education = education;
    }

    private void setHashtags(final List<Hashtag> hashtags) {
        Objects.requireNonNull(hashtags);
        this.hashtags.clear();
        this.hashtags.addAll(hashtags);
    }

    private void setTechStacks(final List<TechStack> techStacks) {
        Objects.requireNonNull(techStacks);
        this.techStacks.clear();
        this.techStacks.addAll(techStacks);
    }

    private void setPortfolios(final List<Portfolio> portfolios) {
        Objects.requireNonNull(portfolios);
        this.portfolios.clear();
        this.portfolios.addAll(portfolios);
    }

    /**
     * 피플 프로필 정보를 수정한다. 이때, 수정하지 않은 정보도 함께 전달해야 한다.
     *
     * @param introduction 소개
     * @param activityArea 활동 지역
     * @param education 학력
     * @param hashtags 해시태그
     * @param techStacks 기술 스택
     * @param portfolios 포트폴리오
     */
    public void edit(
        final String introduction,
        final String activityArea,
        final Education education,
        final List<Hashtag> hashtags,
        final List<TechStack> techStacks,
        final List<Portfolio> portfolios
    ) {
        setIntroduction(introduction);
        setActivityArea(activityArea);
        setEducation(education);
        setHashtags(hashtags);
        setTechStacks(techStacks);
        setPortfolios(portfolios);
    }
}
