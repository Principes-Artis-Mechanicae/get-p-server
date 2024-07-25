package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import es.princip.getp.domain.common.domain.Duration;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project_application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectApplication extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_application_id")
    @Getter
    private Long applicationId;

    // 지원자의 피플 ID
    @Column(name = "people_id")
    private Long applicantId;

    // 지원한 프로젝트 ID
    @Column(name = "project_id")
    private Long projectId;

    // 희망 작업 기간
    @Embedded
    @AttributeOverrides(
        {
            @AttributeOverride(name = "startDate", column = @Column(name = "expected_start_date")),
            @AttributeOverride(name = "endDate", column = @Column(name = "expected_end_date"))
        }
    )
    private Duration expectedDuration;

    // 지원 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProjectApplicationStatus applicationStatus;

    // 지원 내용
    @Column(name = "description")
    private String description;

    // 첨부 파일 목록
    @ElementCollection
    @CollectionTable(name = "project_application_attachment_file", joinColumns = @JoinColumn(name = "project_application_id"))
    private List<AttachmentFile> attachmentFiles = new ArrayList<>();

    @Builder
    public ProjectApplication(
        final Long applicantId,
        final Long projectId,
        final Duration expectedDuration,
        final ProjectApplicationStatus applicationStatus,
        final String description,
        final List<AttachmentFile> attachmentFiles
    ) {
        this.applicantId = applicantId;
        this.projectId = projectId;
        this.expectedDuration = expectedDuration;
        this.applicationStatus = applicationStatus;
        this.description = description;
        this.attachmentFiles = attachmentFiles;
    }
}
