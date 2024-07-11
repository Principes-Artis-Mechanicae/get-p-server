package es.princip.getp.domain.project.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_application_attachment_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectApplicationAttachmentFile extends BaseTimeEntity {

    @Id
    @Column(name = "project_application_attachment_file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_application_id")
    private ProjectApplication projectApplication;

    @Embedded
    private AttachmentFile attachmentFile;

    private ProjectApplicationAttachmentFile(ProjectApplication projectApplication, AttachmentFile attachmentFile) {
        this.projectApplication = projectApplication;
        this.attachmentFile = attachmentFile;
    }

    public static ProjectApplicationAttachmentFile of(ProjectApplication projectApplication, String attachmentUri) {
        return new ProjectApplicationAttachmentFile(projectApplication, AttachmentFile.from(attachmentUri));
    }

    public String getUri() {
        return attachmentFile.getUri();
    }
}
