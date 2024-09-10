package es.princip.getp.persistence.adapter.like.project;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "project_like")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectLikeJpaEntity {

    @Id
    @Column(name = "project_like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "people_id")
    private Long peopleId;

    @Column(name = "project_id")
    private Long projectId;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP")
    protected LocalDateTime createdAt;

    @Builder
    public ProjectLikeJpaEntity(
        final Long id,
        final Long peopleId,
        final Long projectId,
        final LocalDateTime createdAt
    ) {
        this.id = id;
        this.peopleId = peopleId;
        this.projectId = projectId;
        this.createdAt = createdAt;
    }
}