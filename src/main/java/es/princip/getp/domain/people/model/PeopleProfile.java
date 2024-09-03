package es.princip.getp.domain.people.model;

import es.princip.getp.domain.common.model.BaseModel;
import es.princip.getp.domain.common.model.Hashtag;
import es.princip.getp.domain.common.model.TechStack;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class PeopleProfile extends BaseModel {

    @NotBlank private final String introduction;
    @NotBlank private final String activityArea;
    @NotNull private final Education education;
    private final List<@NotNull Hashtag> hashtags;
    private final List<@NotNull TechStack> techStacks;
    private final List<@NotNull Portfolio> portfolios;

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

        validate();
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
