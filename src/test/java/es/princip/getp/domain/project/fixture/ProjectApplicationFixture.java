package es.princip.getp.domain.project.fixture;

import es.princip.getp.domain.project.command.domain.ExpectedDuration;
import es.princip.getp.domain.project.command.domain.ProjectApplication;
import es.princip.getp.domain.project.command.domain.ProjectApplicationStatus;

import java.time.LocalDate;

public class ProjectApplicationFixture {

    public static ProjectApplication projectApplication(final Long peopleId, final Long projectID) {
        return ProjectApplication.builder()
            .applicantId(peopleId)
            .projectId(projectID)
            .expectedDuration(ExpectedDuration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ))
            .applicationStatus(ProjectApplicationStatus.APPLICATION_ACCEPTED)
            .description("프로젝트 지원 내용")
            .build();
    }
}