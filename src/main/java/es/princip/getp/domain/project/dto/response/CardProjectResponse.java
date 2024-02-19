package es.princip.getp.domain.project.dto.response;

import es.princip.getp.domain.client.dto.response.CardProjectClientResponse;
import es.princip.getp.domain.hashtag.entity.Hashtag;
import es.princip.getp.domain.project.entity.Project;
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
    @NotNull CardProjectClientResponse client
) {

    public static CardProjectResponse from(final Project project) {
        return new CardProjectResponse(
            project.getProjectId(),
            project.getTitle(),
            project.getPayment(),
            project.getApplicationDeadline().getValue(),
            project.getHashtags().stream().map(Hashtag::getValue).toList(),
            project.getInterestsCount(),
            CardProjectClientResponse.from(project.getClient())
        );
    }
}
