package es.princip.getp.persistence.adapter.project.confirmation.model;

import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "project_confirmation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectConfirmationJpaEntity extends BaseTimeJpaEntity {
    @Id
    @Column(name = "project_confirmation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "people_id")
    private Long applicantId;
}
 