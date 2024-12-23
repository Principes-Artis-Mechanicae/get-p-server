package es.princip.getp.application.project.confirmation;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.apply.port.out.CheckProjectApplicationPort;
import es.princip.getp.application.project.apply.port.out.LoadProjectApplicantPort;
import es.princip.getp.application.project.apply.port.out.UpdateProjectApplicantPort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.application.project.confirmation.dto.command.ConfirmationProjectCommand;
import es.princip.getp.application.project.confirmation.port.in.ConfirmationProjectUseCase;
import es.princip.getp.application.project.confirmation.port.out.SaveProjectConfirmationPort;
import es.princip.getp.application.project.meeting.exception.NotApplicantException;
import es.princip.getp.application.project.meeting.exception.NotClientOfProjectException;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.confirmation.model.ProjectConfirmation;
import es.princip.getp.domain.project.confirmation.model.ProjectConfirmationId;
import es.princip.getp.domain.project.confirmation.service.ProjectConfirmer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectConfirmationService implements ConfirmationProjectUseCase {

    private final SaveProjectConfirmationPort saveProjectConfirmationPort;
    private final UpdateProjectApplicantPort updateProjectApplicantPort;
    private final LoadProjectApplicantPort loadProjectApplicantPort;
    private final LoadPeoplePort loadPeoplePort;
    private final LoadClientPort loadClientPort;
    private final LoadProjectPort loadProjectPort;

    private final CheckProjectApplicationPort checkProjectApplicationPort;

    private final ProjectConfirmer projectConfirmer;

    /**
     * 프로젝트 확정
     * @param command 프로젝트 확정 명령
     * @return ProjectConfirmation ID
     */
    @Transactional
    public ProjectConfirmationId confirm(ConfirmationProjectCommand command) {
        Client client = loadClientPort.loadBy(command.memberId());
        Project project = loadProjectPort.loadBy(command.projectId());

        checkPeopleIsApplicant(command.applicantId(), command.projectId());
        checkClientOfProject(client, project);

        final People people = loadPeoplePort.loadBy(command.applicantId());
        final ProjectApplication projectApplication = loadProjectApplicantPort.loadBy(command.projectId(), command.applicantId());

        final ProjectConfirmation projectConfirmation = projectConfirmer.confirm(project, people, projectApplication);
        projectApplicationConfirm(projectApplication);

        ProjectConfirmationId id = new ProjectConfirmationId(saveProjectConfirmationPort.save(projectConfirmation));
        return id;
    }

    private void checkClientOfProject(final Client client, final Project project) {
        if (!project.isClient(client)) {
            throw new NotClientOfProjectException();
        }
    }

    private void checkPeopleIsApplicant(final PeopleId applicantId, final ProjectId projectId) {
        if (!checkProjectApplicationPort.existsBy(applicantId, projectId)) {
            throw new NotApplicantException();
        }
    }

    private void projectApplicationConfirm(ProjectApplication projectApplication) {
        projectApplication.confirm();
        updateProjectApplicantPort.update(projectApplication);
    }
}
