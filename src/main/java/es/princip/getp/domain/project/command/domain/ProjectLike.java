package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "project_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_like_id")
    private Long id;

    @Column(name = "people_id")
    private Long peopleId;

    @Column(name = "project_id")
    private Long projectId;

    private ProjectLike(final Long peopleId, final Long projectId) {
        this.peopleId = peopleId;
        this.projectId = projectId;
    }

    public static ProjectLike of(final Long peopleId, final Long projectId) {
        return new ProjectLike(peopleId, projectId);
    }
}