package es.princip.getp.fixture.project;

import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.IndividualProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.TeamProjectApplication;
import es.princip.getp.domain.project.commission.model.ProjectId;

import java.time.LocalDate;
import java.util.stream.LongStream;

import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.ACCEPTED;
import static es.princip.getp.fixture.project.AttachmentFileFixture.attachmentFiles;

public class ProjectApplicationFixture {

    public static final String DESCRIPTION = "지원 내용";

    public static ProjectApplication individualProjectApplication(
        final PeopleId peopleId,
        final ProjectId projectId
    ) {
        return IndividualProjectApplication.builder()
            .applicantId(peopleId)
            .projectId(projectId)
            .expectedDuration(Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ))
            .status(ACCEPTED)
            .description(DESCRIPTION)
            .attachmentFiles(attachmentFiles())
            .build();
    }

    public static ProjectApplication teamProjectApplication(
        final PeopleId peopleId,
        final ProjectId projectId
    ) {
        return TeamProjectApplication.builder()
            .applicantId(peopleId)
            .projectId(projectId)
            .expectedDuration(Duration.of(
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 7, 31)
            ))
            .status(ACCEPTED)
            .description(DESCRIPTION)
            .attachmentFiles(attachmentFiles())
            .teams(LongStream.rangeClosed(1, 4)
                .boxed()
                .map(PeopleId::new)
                .toList())
            .build();
    }
}
