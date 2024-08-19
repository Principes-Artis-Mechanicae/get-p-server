package es.princip.getp.api.controller.project.query.dto;

import es.princip.getp.common.domain.Duration;
import es.princip.getp.common.dto.HashtagsResponse;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectStatus;

public record AppliedProjectCardResponse(
    Long projectId,
    String title,
    Long payment,
    Long applicantsCount,
    Long estimatedDays,
    Duration applicationDuration,
    HashtagsResponse hashtags,
    String description,
    ProjectStatus status
) {

    public static AppliedProjectCardResponse of(final Project project, final Long applicantsCount) {
        return new AppliedProjectCardResponse(
            project.getProjectId(),
            project.getTitle(),
            project.getPayment(),
            applicantsCount,
            project.getEstimatedDuration().days(),
            project.getApplicationDuration(),
            HashtagsResponse.from(project.getHashtags()),
            project.getDescription(),
            project.getStatus()
        );
    }
}