package es.princip.getp.domain.people.entity;

import java.util.ArrayList;
import java.util.List;
import es.princip.getp.domain.people.dto.request.UpdatePeopleProfileRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "people_profile")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_profile_id")
    private Long peopleProfileId;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "activity_area")
    private String activityArea;

    @Column(name = "education")
    private String education;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "people_id")
    private People people;
    
    @OneToMany(mappedBy = "peopleProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PeopleHashtag> hashtags = new ArrayList<>();

    @OneToMany(mappedBy = "peopleProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PeopleTechStack> techStacks = new ArrayList<>();

    @Builder
    public PeopleProfile(final String introduction,
        final String activityArea,
        final String education,
        final List<String> hashtags,
        final List<String> techStacks,
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
        this.people = people;
    }

    public void update(UpdatePeopleProfileRequest request) {
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
    }
}
