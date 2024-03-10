package es.princip.getp.domain.people.entity;

import es.princip.getp.domain.base.BaseTimeEntity;
import es.princip.getp.domain.techStack.values.TechStack;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "people_tech_stack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PeopleTechStack extends BaseTimeEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_tech_stack_id")
    private Long peopleTechStackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "people_profile_id")
    private PeopleProfile peopleProfile;

    @Embedded
    private TechStack techStack;

    private PeopleTechStack(PeopleProfile peopleProfile, TechStack techStack) {
        this.peopleProfile = peopleProfile;
        this.techStack = techStack;
    }

    public static PeopleTechStack of(PeopleProfile peopleProfile, String techStack) {
        return new PeopleTechStack(peopleProfile, TechStack.from(techStack));
    }

    public String getValue() {
        return techStack.getValue();
    }
}
