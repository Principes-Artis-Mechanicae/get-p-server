package es.princip.getp.persistence.adapter.project.apply.model;

import es.princip.getp.domain.project.apply.model.TeammateStatus;
import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.ConstraintMode.NO_CONSTRAINT;

@Getter
@Entity
@Builder
@AllArgsConstructor
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
}
