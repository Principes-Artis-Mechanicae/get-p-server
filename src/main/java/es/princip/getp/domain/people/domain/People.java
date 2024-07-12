package es.princip.getp.domain.people.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.member.domain.model.Email;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class People extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_id")
    private Long peopleId;

    @Column(name = "email")
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name = "people_type")
    private PeopleType peopleType;

    @Column(name = "member_id")
    private Long memberId;

    @ElementCollection
    @CollectionTable(
        name = "people_project_like",
        joinColumns = @JoinColumn(name = "client_id")
    )
    private Set<Long> projectLikes = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "people_profile_id")
    private PeopleProfile profile;

    @Builder
    public People(
        final Email email,
        final PeopleType peopleType,
        final Long memberId
    ) {
        this.email = email;
        this.peopleType = peopleType;
        this.memberId = memberId;
    }

    public void changeEmail(Email email) {
        this.email = email;
    }

    public void changePeopleType(PeopleType peopleType) {
        this.peopleType = peopleType;
    }

    public void writeProfile(Long memberId, PeopleProfile profile) {
        if (!this.memberId.equals(memberId)) {
            throw new IllegalArgumentException("MemberId is not matched");
        }
        this.profile = profile;
    }

    public void editProfile(Long memberId, PeopleProfile profile) {
        if (!this.memberId.equals(memberId)) {
            throw new IllegalArgumentException("MemberId is not matched");
        }
        this.profile = profile;
    }

    private boolean alreadyLikedPeople(Long projectId) {
        return projectLikes.contains(projectId);
    }

    public void likeProject(Long projectId) {
        if (alreadyLikedPeople(projectId)) {
            throw new IllegalArgumentException("이미 좋아요를 누른 사람입니다.");
        }
        projectLikes.add(projectId);
    }
}
