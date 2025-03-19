package es.princip.getp.persistence.adapter.project.assign.model;

import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "assignment_people")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AssignmentPeopleJpaEntity extends BaseTimeJpaEntity {
    @Id
    @Column(name = "assignment_people_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "people_id")
    private Long applicantId;
}
 