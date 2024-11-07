package es.princip.getp.application.project.commission.dto.response;

import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.common.model.Hashtag;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectStatus;

import java.util.List;

public record ProjectCardResponse(
    Long projectId,
    String title,
    Long payment,
    Long recruitmentCount,
    Long applicantsCount,
    Long estimatedDays,
    Duration applicationDuration,
    List<String> hashtags,
    String description,
    ProjectStatus status
) {
    public static ProjectCardResponse of(final Project project, final Long applicantsCount) {
        return new ProjectCardResponse(
            project.getId().getValue(),
            project.getTitle(),
            project.getPayment(),
            project.getRecruitmentCount(),
            applicantsCount,
            project.getEstimatedDuration().days(),
            project.getApplicationDuration(),
            project.getHashtags().stream()
                .map(Hashtag::getValue)
                .toList(),
            project.getDescription(),
            project.getStatus()
        );
    }
}
