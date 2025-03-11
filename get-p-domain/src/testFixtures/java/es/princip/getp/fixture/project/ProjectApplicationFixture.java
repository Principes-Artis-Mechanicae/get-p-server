package es.princip.getp.fixture.project;

import es.princip.getp.domain.common.model.Duration;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.*;
import es.princip.getp.domain.project.commission.model.ProjectId;

import java.time.LocalDate;
import java.util.Set;

import static es.princip.getp.domain.project.apply.model.ProjectApplicationStatus.ACCEPTED;
import static es.princip.getp.fixture.project.AttachmentFileFixture.attachmentFiles;

public class ProjectApplicationFixture {

    public static final String DESCRIPTION = "지원 내용";

    public static Duration expectedDuration() {
        return Duration.of(
            LocalDate.of(2024, 7, 1),
            LocalDate.of(2024, 7, 31)
        );
    }

    private static IndividualProjectApplication.IndividualProjectApplicationBuilder individualBuilder() {
        return IndividualProjectApplication.builder()
            .expectedDuration(expectedDuration())
            .status(ACCEPTED)
            .description(DESCRIPTION)
            .attachmentFiles(attachmentFiles());
    }

    private static TeamProjectApplication.TeamProjectApplicationBuilder teamBuilder() {
        return TeamProjectApplication.builder()
            .expectedDuration(expectedDuration())
            .description(DESCRIPTION)
            .attachmentFiles(attachmentFiles());
    }

    public static ProjectApplication individualProjectApplication(
        final PeopleId peopleId,
        final ProjectId projectId
    ) {
        return individualBuilder()
            .applicantId(peopleId)
            .projectId(projectId)
            .build();
    }

    public static ProjectApplication individualProjectApplication(
        final PeopleId peopleId,
        final ProjectId projectId,
        final ProjectApplicationStatus status
    ) {
        return individualBuilder()
            .applicantId(peopleId)
            .projectId(projectId)
            .status(status)
            .build();
    }

    public static ProjectApplication individualProjectApplication(
        final ProjectApplicationId applicationId,
        final PeopleId peopleId,
        final ProjectId projectId
    ) {
        return individualBuilder()
            .id(applicationId)
            .applicantId(peopleId)
            .projectId(projectId)
            .build();
    }

    public static TeamProjectApplication teamProjectApplication(
        final PeopleId peopleId,
        final ProjectId projectId,
        final ProjectApplicationStatus status,
        final Set<Teammate> teammates
    ) {
        return teamBuilder()
            .applicantId(peopleId)
            .projectId(projectId)
            .status(status)
            .teammates(teammates)
            .build();
    }

    public static TeamProjectApplication teamProjectApplication(
        final ProjectApplicationId applicationId,
        final PeopleId peopleId,
        final ProjectId projectId,
        final ProjectApplicationStatus status,
        final Set<Teammate> teammates
    ) {
        return teamBuilder()
            .id(applicationId)
            .applicantId(peopleId)
            .projectId(projectId)
            .status(status)
            .teammates(teammates)
            .build();
    }

    public static Teammate teammate(
        final PeopleId peopleId,
        final TeammateStatus status
    ) {
        return Teammate.builder()
            .peopleId(peopleId)
            .status(status)
            .build();
    }

    public static Teammate teammate(
        final TeammateId teammateId,
        final PeopleId peopleId,
        final TeammateStatus status
    ) {
        return Teammate.builder()
            .id(teammateId)
            .peopleId(peopleId)
            .status(status)
            .build();
    }
}
