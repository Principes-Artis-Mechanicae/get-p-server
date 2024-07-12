package es.princip.getp.domain.project.dto.response;

import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.presentation.dto.response.ProjectClientResponse;
import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.project.domain.AttachmentFile;
import es.princip.getp.domain.project.domain.MeetingType;
import es.princip.getp.domain.project.domain.Project;
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

    public static DetailProjectResponse from(final Project project, final Client client, final Member member) {
        return new DetailProjectResponse(
            project.getProjectId(),
            project.getTitle(),
            project.getPayment(),
            project.getApplicationDeadline().getValue(),
            project.getEstimatedDuration().getStartDate(),
            project.getEstimatedDuration().getEndDate(),
            project.getDescription(),
            project.getMeetingType(),
            project.getAttachmentFiles().stream().map(AttachmentFile::getUri).toList(),
            project.getHashtags().stream().map(Hashtag::getValue).toList(),
            0L,
            ProjectClientResponse.from(client, member)
        );
    }
}
