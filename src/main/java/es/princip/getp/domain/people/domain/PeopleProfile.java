package es.princip.getp.domain.people.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.project.domain.TechStack;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
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
    @Getter
    private String introduction;

    @Column(name = "activity_area")
    @Getter
    private String activityArea;

    @Embedded
    @Getter
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

    public List<Hashtag> getHashtags() {
        return Collections.unmodifiableList(hashtags);
    }

    public List<TechStack> getTechStacks() {
        return Collections.unmodifiableList(techStacks);
    }

    public List<Portfolio> getPortfolios() {
        return Collections.unmodifiableList(portfolios);
    }
}
