package es.princip.getp.domain.project.apply.service;

import es.princip.getp.domain.people.exception.NotRegisteredPeopleProfileException;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.apply.exception.ClosedProjectApplicationException;
import es.princip.getp.domain.project.apply.model.*;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectApplier {

    public ProjectApplication apply(
        final People applicant,
        final Project project,
        final ProjectApplicationData data
    ) {
        if (project.isApplicationClosed()) {
            throw new ClosedProjectApplicationException();
        }
        if (!applicant.isProfileRegistered()) {
            throw new NotRegisteredPeopleProfileException();
        }
        if (data instanceof TeamProjectApplicationData teamData) {
            return buildTeamProjectApplication(applicant, project, teamData);
        }
        if (data instanceof IndividualProjectApplicationData individualData) {
            return buildIndividualProjectApplication(applicant, project, individualData);
        }
        throw new IllegalArgumentException("올바르지 않은 프로젝트 지원 유형: " + data.getClass());
    }

    private ProjectApplication buildTeamProjectApplication(
        final People applicant,
        final Project project,
        final TeamProjectApplicationData data
    ) {
        return TeamProjectApplication.builder()
            .applicantId(applicant.getId())
            .projectId(project.getId())
            .expectedDuration(data.getExpectedDuration())
            .description(data.getDescription())
            .attachmentFiles(data.getAttachmentFiles())
            .teams(data.getTeams())
            .status(ProjectApplicationStatus.PENDING_TEAM_APPROVAL)
            .build();
    }

    private ProjectApplication buildIndividualProjectApplication(
        final People applicant,
        final Project project,
        final IndividualProjectApplicationData data
    ) {
        return IndividualProjectApplication.builder()
            .applicantId(applicant.getId())
            .projectId(project.getId())
            .expectedDuration(data.getExpectedDuration())
            .description(data.getDescription())
            .attachmentFiles(data.getAttachmentFiles())
            .status(ProjectApplicationStatus.COMPLETED)
            .build();
    }
}
