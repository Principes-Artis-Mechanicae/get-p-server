package es.princip.getp.domain.project.dto.response;

import es.princip.getp.domain.client.domain.Client;
import es.princip.getp.domain.client.presentation.dto.response.ProjectClientResponse;
import es.princip.getp.domain.common.domain.Hashtag;
import es.princip.getp.domain.member.domain.model.Member;
import es.princip.getp.domain.project.domain.Project;
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

    public static CardProjectResponse from(final Project project, final Client client, final Member member) {
        return new CardProjectResponse(
            project.getProjectId(),
            project.getTitle(),
            project.getPayment(),
            project.getApplicationDeadline().getValue(),
            project.getHashtags().stream().map(Hashtag::getValue).toList(),
            project.getInterestsCount(),
            ProjectClientResponse.from(client, member)
        );
    }
}
