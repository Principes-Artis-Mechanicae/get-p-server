package es.princip.getp.domain.project.apply.model;

import es.princip.getp.domain.common.model.AttachmentFile;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.COMPLETED;

@Getter
public class IndividualProjectApplication extends ProjectApplication {

    public static final String TYPE = "INDIVIDUAL";

    @Builder
    public IndividualProjectApplication(
        final ProjectApplicationId id,
        final PeopleId applicantId,
        final ProjectId projectId,
        final Duration expectedDuration,
        final ProjectApplicationStatus status,
        final String description,
        final List<AttachmentFile> attachmentFiles,
        final LocalDateTime createdAt,
        final LocalDateTime updatedAt
    ) {
        super(
            id,
            applicantId,
            projectId,
            expectedDuration,
            status,
            description,
            attachmentFiles,
            createdAt,
            updatedAt
        );
    }

    public static ProjectApplication of(
        final PeopleId applicantId,
        final ProjectId projectId,
        final ProjectApplicationData data
    ) {
        return IndividualProjectApplication.builder()
            .applicantId(applicantId)
            .projectId(projectId)
            .expectedDuration(data.getExpectedDuration())
            .description(data.getDescription())
            .attachmentFiles(data.getAttachmentFiles())
            .status(COMPLETED)
            .build();
    }
}
