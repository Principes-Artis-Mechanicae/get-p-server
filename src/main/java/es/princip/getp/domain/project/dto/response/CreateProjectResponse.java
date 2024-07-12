package es.princip.getp.domain.project.dto.response;

import es.princip.getp.domain.project.domain.Project;
import jakarta.validation.constraints.NotNull;

public record CreateProjectResponse(
    @NotNull Long projectId
) {

    public static CreateProjectResponse from(final Project project) {
        return new CreateProjectResponse(
            project.getProjectId()
        );
    }
}