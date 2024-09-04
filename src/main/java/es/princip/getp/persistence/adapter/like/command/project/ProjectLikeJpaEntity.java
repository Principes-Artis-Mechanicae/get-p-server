package es.princip.getp.persistence.adapter.like.command.project;

import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "project_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectLikeJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "people_id")
    private Long peopleId;

    @Column(name = "project_id")
    private Long projectId;

    @Builder
    public ProjectLikeJpaEntity(final Long peopleId, final Long projectId) {
        this.peopleId = peopleId;
        this.projectId = projectId;
    }
}