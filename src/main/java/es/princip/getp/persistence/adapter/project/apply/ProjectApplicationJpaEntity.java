package es.princip.getp.persistence.adapter.project.apply;

import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import es.princip.getp.persistence.adapter.common.DurationJpaVO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@Table(name = "project_application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class ProjectApplicationJpaEntity extends BaseTimeJpaEntity {

    @Id
    @Column(name = "project_application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @Column(name = "people_id")
    private Long applicantId;

    @Column(name = "project_id")
    private Long projectId;

    @Embedded
    @AttributeOverrides(
        {
            @AttributeOverride(name = "startDate", column = @Column(name = "expected_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "expected_end_date"))
        }
    )
    private DurationJpaVO expectedDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectApplicationStatus applicationStatus;

    @Column(name = "description")
    private String description;

    @Builder.Default
    @ElementCollection
    @CollectionTable(
        name = "project_application_attachment_file",
        joinColumns = @JoinColumn(name = "project_application_id")
    )
    private List<String> attachmentFiles = new ArrayList<>();
}
