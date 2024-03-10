package es.princip.getp.domain.project.dto.response;

import es.princip.getp.domain.client.dto.response.ProjectClientResponse;
import es.princip.getp.domain.project.domain.entity.Project;
import es.princip.getp.domain.project.domain.entity.ProjectHashtag;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record CardProjectResponse(
    @NotNull Long projectId,
    @NotNull String title,
    @NotNull Long payment,
    @NotNull LocalDate applicationDeadline,
    @NotNull List<String> hashtags,
    int interestsCount,
    @NotNull ProjectClientResponse client
) {

    public static CardProjectResponse from(final Project project) {
        return new CardProjectResponse(
            project.getProjectId(),
            project.getTitle(),
            project.getPayment(),
            project.getApplicationDeadline().getValue(),
            project.getHashtags().stream().map(ProjectHashtag::getValue).toList(),
            project.getInterestsCount(),
            ProjectClientResponse.from(project.getClient())
        );
    }
}
