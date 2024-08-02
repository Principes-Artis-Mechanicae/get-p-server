package es.princip.getp.domain.project.query.dto;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.common.dto.HashtagsResponse;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectStatus;

public record MyProjectCardResponse(
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

    public static MyProjectCardResponse of(final Project project, final Long applicantsCount) {
        return new MyProjectCardResponse(
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