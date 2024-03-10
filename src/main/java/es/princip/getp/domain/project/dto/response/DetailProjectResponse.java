package es.princip.getp.domain.project.dto.response;

import es.princip.getp.domain.client.dto.response.ProjectClientResponse;
import es.princip.getp.domain.project.domain.entity.Project;
import es.princip.getp.domain.project.domain.entity.ProjectAttachmentFile;
import es.princip.getp.domain.project.domain.entity.ProjectHashtag;
import es.princip.getp.domain.project.domain.enums.MeetingType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record DetailProjectResponse(
    @NotNull Long projectId,
    @NotNull String title,
    @NotNull Long payment,
    @NotNull LocalDate applicationDeadline,
    @NotNull LocalDate estimatedStartDate,
    @NotNull LocalDate estimatedEndDate,
    @NotNull String description,
    @NotNull MeetingType meetingType,
    @NotNull List<String> attachmentUris,
    @NotNull List<String> hashtags,
    @NotNull Long interestCount,
    @NotNull ProjectClientResponse client
) {

    public static DetailProjectResponse from(final Project project) {
        return new DetailProjectResponse(
            project.getProjectId(),
            project.getTitle(),
            project.getPayment(),
            project.getApplicationDeadline().getValue(),
            project.getEstimatedDuration().getStartDate(),
            project.getEstimatedDuration().getEndDate(),
            project.getDescription(),
            project.getMeetingType(),
            project.getAttachmentFiles().stream().map(ProjectAttachmentFile::getUri).toList(),
            project.getHashtags().stream().map(ProjectHashtag::getValue).toList(),
            0L,
            ProjectClientResponse.from(project.getClient())
        );
    }
}
