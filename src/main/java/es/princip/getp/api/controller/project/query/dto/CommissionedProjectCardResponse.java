package es.princip.getp.api.controller.project.query.dto;

import es.princip.getp.api.controller.common.dto.HashtagsResponse;
import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectStatus;

public record CommissionedProjectCardResponse(
    Long projectId,
    String title,
    Long payment,
    Long recruitmentCount,
    Long applicantsCount,
    Long estimatedDays,
    Duration applicationDuration,
    HashtagsResponse hashtags,
    String description,
    ProjectStatus status
) {

    public static CommissionedProjectCardResponse of(final Project project, final Long applicantsCount) {
        return new CommissionedProjectCardResponse(
            project.getId().getValue(),
            project.getTitle(),
            project.getPayment(),
            project.getRecruitmentCount(),
            applicantsCount,
            project.getEstimatedDuration().days(),
            project.getApplicationDuration(),
            HashtagsResponse.from(project.getHashtags()),
            project.getDescription(),
            project.getStatus()
        );
    }
}
