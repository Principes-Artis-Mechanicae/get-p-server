package es.princip.getp.domain.project.domain;

import es.princip.getp.domain.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "project_attachment_file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectAttachmentFile extends BaseTimeEntity {

    @Id
    @Column(name = "project_attachment_file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectAttachmentFileId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Embedded
    private AttachmentFile attachmentFile;

    private ProjectAttachmentFile(Project project, AttachmentFile attachmentFile) {
        this.project = project;
        this.attachmentFile = attachmentFile;
    }

    public static ProjectAttachmentFile of(Project project, String attachmentUri) {
        return new ProjectAttachmentFile(project, AttachmentFile.from(attachmentUri));
    }

    public String getUri() {
        return attachmentFile.getUri();
    }
}
