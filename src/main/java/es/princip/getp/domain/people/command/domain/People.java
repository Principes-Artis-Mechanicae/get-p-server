package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.people.exception.PeopleLikeErrorCode;
import es.princip.getp.infra.exception.BusinessLogicException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class People extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_id")
    @Getter
    private Long peopleId;

    @Column(name = "email")
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name = "people_type")
    private PeopleType peopleType;

    @Column(name = "member_id")
    @Getter
    private Long memberId;

    @ElementCollection
    @CollectionTable(
        name = "people_project_like",
        joinColumns = @JoinColumn(name = "people_id")
    )
    private Set<Long> likedProjects = new HashSet<>();

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

    private void setEmail(Email email) {
        this.email = email;
    }

    private void setPeopleType(PeopleType peopleType) {
        if (peopleType == null) {
            throw new IllegalArgumentException();
        }
        this.peopleType = peopleType;
    }

    public void edit(
        final Long memberId,
        final Email email,
        final PeopleType peopleType
    ) {
        if (!this.memberId.equals(memberId)) {
            throw new IllegalArgumentException("본인의 회원 정보만 수정할 수 있습니다.");
        }
        setEmail(email);
        setPeopleType(peopleType);
    }

    private boolean alreadyLikedProject(Long projectId) {
        return likedProjects.contains(projectId);
    }

    public void likeProject(Long projectId) {
        if (alreadyLikedProject(projectId)) {
            throw new BusinessLogicException(PeopleLikeErrorCode.ALREADY_LIKED);
        }
        likedProjects.add(projectId);
    }
}
