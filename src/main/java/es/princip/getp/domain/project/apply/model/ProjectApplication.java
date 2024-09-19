package es.princip.getp.domain.project.apply.model;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.support.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ProjectApplication extends BaseEntity {

    private ProjectApplicationId id;
    @NotNull private final PeopleId applicantId;
    @NotNull private final ProjectId projectId;
    @NotNull private Duration expectedDuration;
    @NotNull private ProjectApplicationStatus applicationStatus;
    @NotBlank private String description;
    private final List<@NotNull AttachmentFile> attachmentFiles;

    @Builder
    public ProjectApplication(
        final ProjectApplicationId id,
        final PeopleId applicantId,
        final ProjectId projectId,
        final Duration expectedDuration,
        final ProjectApplicationStatus applicationStatus,
        final String description,
        final List<AttachmentFile> attachmentFiles,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(createdAt, updatedAt);

        this.id = id;
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
