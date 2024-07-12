package es.princip.getp.domain.project.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "project_application")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectApplication extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_application_id")
    private Long applicationId;

    @Column(name = "people_id")
    @Getter
    private Long applicant;

    // 희망 작업 기간
    @Embedded
    @Getter
    private ExpectedDuration expectedDuration;

    @Enumerated(EnumType.STRING)
    @Column(name = "application_status")
    @Getter
    private ProjectApplicationStatus applicationStatus;

    // 지원 내용
    @Column(name = "description")
    @Getter
    private String description;

    // 첨부 파일 목록
    @ElementCollection
    @CollectionTable(name = "project_application_attachment_file", joinColumns = @JoinColumn(name = "project_application_id"))
    @Builder.Default
    private List<AttachmentFile> attachmentFiles = new ArrayList<>();

    public List<AttachmentFile> getAttachmentFiles() {
        return Collections.unmodifiableList(attachmentFiles);
    }
}
