package es.princip.getp.domain.project.dto.request;

import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.project.domain.entity.Project;
import es.princip.getp.domain.project.domain.entity.ProjectApplication;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record ApplicateProjectRequest(
    @NotNull LocalDate expectedStartDate,
    @NotNull LocalDate expectedEndDate,
    @NotNull String description,
    @NotNull List<String> attachmentUris
) {
    public ProjectApplication toEntity(People people, Project project) {
        return ProjectApplication.builder()
            .expectedStartDate(expectedStartDate)
            .expectedEndDate(expectedEndDate)
            .description(description)
            .attachmentUris(attachmentUris)
            .people(people)
            .project(project)
            .build();
    }
}
