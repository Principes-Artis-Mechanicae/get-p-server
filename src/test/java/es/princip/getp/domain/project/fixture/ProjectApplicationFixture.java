package es.princip.getp.domain.project.fixture;

import es.princip.getp.domain.common.domain.Duration;
import es.princip.getp.domain.project.command.domain.ProjectApplication;
import es.princip.getp.domain.project.command.domain.ProjectApplicationStatus;

import java.time.LocalDate;

public class ProjectApplicationFixture {

    public static ProjectApplication projectApplication(final Long peopleId, final Long projectId) {
        return ProjectApplication.builder()
            .applicantId(peopleId)
            .projectId(projectId)
            .expectedDuration(Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ))
            .applicationStatus(ProjectApplicationStatus.APPLICATION_ACCEPTED)
            .description("프로젝트 지원 내용")
            .build();
    }
}
