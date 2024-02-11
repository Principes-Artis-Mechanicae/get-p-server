package es.princip.getp.domain.project.dto.response;

import es.princip.getp.domain.hashtag.entity.Hashtag;
import es.princip.getp.domain.project.entity.AttachmentFile;
import es.princip.getp.domain.project.entity.Project;
import es.princip.getp.domain.project.enums.MeetingType;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CreateProjectResponse(
    @NotNull Long projectId,
    @NotNull String title,
    @NotNull BigDecimal payment,
    @NotNull LocalDate applicationDeadline,
    @NotNull LocalDate estimatedStartDate,
    @NotNull LocalDate estimatedEndDate,
    @NotNull String description,
    @NotNull MeetingType meetingType,
    @NotNull List<String> attachmentUris,
    @NotNull List<String> hashtags
) {

    public static CreateProjectResponse from(Project project) {
        return new CreateProjectResponse(
            project.getProjectId(),
            project.getTitle(),
            project.getPayment(),
            project.getApplicationDeadline().getValue(),
            project.getEstimatedDuration().getStartDate(),
            project.getEstimatedDuration().getEndDate(),
            project.getDescription(),
            project.getMeetingType(),
            project.getAttachmentFiles().stream().map(AttachmentFile::getUri).toList(),
            project.getHashtags().stream().map(Hashtag::getValue).toList()
        );
    }
}