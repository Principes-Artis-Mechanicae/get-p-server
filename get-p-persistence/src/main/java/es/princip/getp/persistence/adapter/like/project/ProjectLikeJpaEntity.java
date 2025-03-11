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

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "project_id")
    private Long projectId;

    @CreatedDate
    @Column(columnDefinition = "TIMESTAMP")
    protected LocalDateTime createdAt;

    @Builder
    public ProjectLikeJpaEntity(
        final Long id,
        final Long memberId,
        final Long projectId,
        final LocalDateTime createdAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.projectId = projectId;
        this.createdAt = createdAt;
    }
}