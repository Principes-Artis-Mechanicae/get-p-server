package es.princip.getp.domain.people.domain;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.project.domain.TechStack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "people_tech_stack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleTechStack extends BaseTimeEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_tech_stack_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "people_profile_id")
    private PeopleProfile peopleProfile;

    @Embedded
    @Getter
    private TechStack techStack;

    private PeopleTechStack(PeopleProfile peopleProfile, TechStack techStack) {
        this.peopleProfile = peopleProfile;
        this.techStack = techStack;
    }

    public static PeopleTechStack of(PeopleProfile peopleProfile, TechStack techStack) {
        return new PeopleTechStack(peopleProfile, techStack);
    }

    public String getValue() {
        return techStack.getValue();
    }
}
