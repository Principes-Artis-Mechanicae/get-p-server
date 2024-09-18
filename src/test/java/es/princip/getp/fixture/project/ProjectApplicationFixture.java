package es.princip.getp.fixture.project;

import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplication;

import java.time.LocalDate;

import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.APPLICATION_ACCEPTED;
import static es.princip.getp.fixture.project.AttachmentFileFixture.attachmentFiles;

public class ProjectApplicationFixture {

    public static final String DESCRIPTION = "프로젝트 지원 내용";

    public static ProjectApplication projectApplication(final PeopleId peopleId, final Long projectId) {
        return ProjectApplication.builder()
            .applicantId(peopleId)
            .projectId(projectId)
            .expectedDuration(Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ))
            .applicationStatus(APPLICATION_ACCEPTED)
            .description(DESCRIPTION)
            .attachmentFiles(attachmentFiles())
            .build();
    }
}
