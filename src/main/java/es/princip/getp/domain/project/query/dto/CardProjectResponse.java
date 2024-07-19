package es.princip.getp.domain.project.query.dto;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.common.dto.HashtagsResponse;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectStatus;

public record CardProjectResponse(
    Long projectId,
    String title,
    Long payment,
    Long applicantsCount,
    Duration applicationDuration,
    HashtagsResponse hashtags,
    String description,
    ProjectStatus status
) {

    public static CardProjectResponse from(final Project project) {
        return new CardProjectResponse(
            project.getProjectId(),
            project.getTitle(),
            project.getPayment(),
            0L,
            project.getApplicationDuration(),
            HashtagsResponse.from(project.getHashtags()),
            project.getDescription(),
            project.getStatus()
        );
    }
}
