package es.princip.getp.persistence.adapter.like.project;

import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    public ProjectLikeJpaEntity(
        final Long id,
        final Long peopleId,
        final Long projectId,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.id = id;
        this.peopleId = peopleId;
        this.projectId = projectId;
    }
}