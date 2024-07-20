package es.princip.getp.domain.people.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.member.command.domain.model.Email;
import es.princip.getp.domain.people.exception.ProjectAlreadyLikedException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class People extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "people_id")
    private Long peopleId;

    @Embedded
    @NotNull
    private Email email;

    @Enumerated(EnumType.STRING)
    @Column(name = "people_type")
    @NotNull
    private PeopleType peopleType;

    @Column(name = "member_id")
    @NotNull
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

    public Set<Long> getLikedProjects() {
        return Collections.unmodifiableSet(likedProjects);
    }

    private void setEmail(final Email email) {
        Objects.requireNonNull(email);
        this.email = email;
    }

    private void setPeopleType(final PeopleType peopleType) {
        Objects.requireNonNull(peopleType);
        this.peopleType = peopleType;
    }

    /**
     * 피플 정보를 수정한다. 이때, 수정하지 않은 정보도 함께 전달해야 한다.
     *
     * @param email 이메일
     * @param peopleType 피플 유형
     */
    public void edit(
        final Email email,
        final PeopleType peopleType
    ) {
        setEmail(email);
        setPeopleType(peopleType);
    }

    private boolean alreadyLikedProject(final Long projectId) {
        return likedProjects.contains(projectId);
    }

    /**
     * 프로젝트에게 좋아요를 누른다.
     *
     * @param projectId 좋아요할 프로젝트 ID
     * @throws ProjectAlreadyLikedException 이미 좋아요한 프로젝트일 경우
     */
    public void likeProject(final Long projectId) {
        if (alreadyLikedProject(projectId)) {
            throw new ProjectAlreadyLikedException();
        }
        likedProjects.add(projectId);
    }
}
