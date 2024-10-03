package es.princip.getp.persistence.adapter.project.apply.model;

import es.princip.getp.domain.project.apply.model.ProjectApplicationStatus;
import es.princip.getp.persistence.adapter.BaseTimeJpaEntity;
import es.princip.getp.persistence.adapter.common.DurationJpaVO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "project_application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public abstract class ProjectApplicationJpaEntity extends BaseTimeJpaEntity {

    @Id
    @Column(name = "project_application_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProjectApplicationStatus status;

    @Column(name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(
        name = "project_application_attachment_file",
        joinColumns = @JoinColumn(
            name = "project_application_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
        )
    )
    private List<String> attachmentFiles = new ArrayList<>();

    protected ProjectApplicationJpaEntity(
        final Long id,
        final Long applicantId,
        final Long projectId,
        final DurationJpaVO expectedDuration,
        final ProjectApplicationStatus status,
        final String description,
        final List<String> attachmentFiles,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.applicantId = applicantId;
        this.projectId = projectId;
        this.expectedDuration = expectedDuration;
        this.status = status;
        this.description = description;
        this.attachmentFiles = attachmentFiles;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
