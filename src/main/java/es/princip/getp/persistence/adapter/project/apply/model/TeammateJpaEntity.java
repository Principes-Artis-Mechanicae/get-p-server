package es.princip.getp.persistence.adapter.project.apply.model;

import es.princip.getp.domain.project.apply.model.TeammateStatus;
import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.ConstraintMode.NO_CONSTRAINT;

@Getter
@Entity
@Table(name = "team_project_application_teammate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeammateJpaEntity extends BaseTimeJpaEntity {

    @Id
    @Column(name = "teammate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "people_id")
    private Long peopleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TeammateStatus status;

    @ManyToOne
    @JoinColumn(name = "project_application_id", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private TeamProjectApplicationJpaEntity application;

    @Builder
    public TeammateJpaEntity(
        final Long id,
        final Long peopleId,
        final TeammateStatus status,
        final TeamProjectApplicationJpaEntity application,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);
        this.id = id;
        this.peopleId = peopleId;
        this.status = status;
        this.application = application;
    }
}
