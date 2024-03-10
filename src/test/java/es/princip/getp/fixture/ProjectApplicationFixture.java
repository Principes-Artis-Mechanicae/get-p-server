package es.princip.getp.fixture;

import es.princip.getp.domain.people.domain.entity.People;
import es.princip.getp.domain.project.dto.request.ApplicateProjectRequest;
import es.princip.getp.domain.project.domain.entity.Project;
import es.princip.getp.domain.project.domain.entity.ProjectApplication;
import java.time.LocalDate;
import java.util.List;

public class ProjectApplicationFixture {

    private static final String DESCRIPTION = "description";
    private static final List<String> ATTACHMENT_URIS = List.of("https://example.com/file1.txt");

    public static ApplicateProjectRequest createApplicateProjectRequest() {
        return new ApplicateProjectRequest(
            LocalDate.now(),
            LocalDate.now().plusDays(10),
            DESCRIPTION,
            ATTACHMENT_URIS
        );
    }

    public static ProjectApplication createProjectApplication(People people, Project project) {
        return ProjectApplication.builder()
            .expectedStartDate(LocalDate.now())
            .expectedEndDate(LocalDate.now().plusDays(10))
            .description(DESCRIPTION)
            .attachmentUris(ATTACHMENT_URIS)
            .people(people)
            .project(project)
            .build();
    }
}
