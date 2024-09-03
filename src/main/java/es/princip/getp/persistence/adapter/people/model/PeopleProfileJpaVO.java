package es.princip.getp.persistence.adapter.people.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleProfileJpaVO {

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "activity_area")
    private String activityArea;

    @Column(name = "school")
    private String school;

    @Column(name = "major")
    private String major;

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "people_profile_hashtag", joinColumns = @JoinColumn(name = "people_id"))
    private List<String> hashtags = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "people_profile_tech_stack", joinColumns = @JoinColumn(name = "people_id"))
    private List<String> techStacks = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "people_profile_portfolio", joinColumns = @JoinColumn(name = "people_id"))
    private List<PortfolioJpaVO> portfolios = new ArrayList<>();

    public boolean isNotRegistered() {
        return introduction == null &&
            activityArea == null &&
            school == null &&
            major == null &&
            hashtags.isEmpty() &&
            techStacks.isEmpty() &&
            portfolios.isEmpty();
    }
}
