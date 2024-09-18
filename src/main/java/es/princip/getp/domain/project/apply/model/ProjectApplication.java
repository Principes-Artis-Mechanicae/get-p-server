package es.princip.getp.domain.project.apply.model;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.support.BaseEntity;
import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ProjectApplication extends BaseEntity {

    private Long applicationId;
    @NotNull private final PeopleId applicantId;
    @NotNull private final Long projectId;
    @NotNull private Duration expectedDuration;
    @NotNull private ProjectApplicationStatus applicationStatus;
    @NotBlank private String description;
    private final List<@NotNull AttachmentFile> attachmentFiles;

    @Builder
    public ProjectApplication(
        final Long applicationId,
        final PeopleId applicantId,
        final Long projectId,
        final Duration expectedDuration,
        final ProjectApplicationStatus applicationStatus,
        final String description,
        final List<AttachmentFile> attachmentFiles,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.applicationId = applicationId;
        this.applicantId = applicantId;
        this.projectId = projectId;
        this.expectedDuration = expectedDuration;
        this.applicationStatus = applicationStatus;
        this.description = description;
        this.attachmentFiles = attachmentFiles;

        validate();
    }
    
    public void setStatus(final ProjectApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}
