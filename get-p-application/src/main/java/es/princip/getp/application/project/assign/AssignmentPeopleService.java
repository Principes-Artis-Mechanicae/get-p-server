package es.princip.getp.application.project.assign;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.apply.port.out.CheckProjectApplicationPort;
import es.princip.getp.application.project.apply.port.out.LoadProjectApplicantPort;
import es.princip.getp.application.project.apply.port.out.UpdateProjectApplicantPort;
import es.princip.getp.application.project.assign.dto.command.AssignmentPeopleCommand;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.application.project.assign.port.in.AssignmentPeopleUseCase;
import es.princip.getp.application.project.assign.port.out.SaveAssignmentPeoplePort;
import es.princip.getp.application.project.meeting.exception.NotApplicantException;
import es.princip.getp.application.project.meeting.exception.NotClientOfProjectException;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.confirmation.model.AssignmentPeople;
import es.princip.getp.domain.project.confirmation.model.AssignmentPeopleId;
import es.princip.getp.domain.project.confirmation.service.AssignPeople;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AssignmentPeopleService implements AssignmentPeopleUseCase {

    private final SaveAssignmentPeoplePort saveProjectConfirmationPort;
    private final UpdateProjectApplicantPort updateProjectApplicantPort;
    private final LoadProjectApplicantPort loadProjectApplicantPort;
    private final LoadPeoplePort loadPeoplePort;
    private final LoadClientPort loadClientPort;
    private final LoadProjectPort loadProjectPort;

    private final CheckProjectApplicationPort checkProjectApplicationPort;

    private final AssignPeople assignPeople;

    /**
     * 진행자 할당
     * @param command 진행자 할당 명령
     * @return ProjectConfirmation ID
     */
    @Transactional
    public AssignmentPeopleId assign(AssignmentPeopleCommand command) {
        Client client = loadClientPort.loadBy(command.memberId());
        Project project = loadProjectPort.loadBy(command.projectId());

        checkPeopleIsApplicant(command.applicantId(), command.projectId());
        checkClientOfProject(client, project);

        final People people = loadPeoplePort.loadBy(command.applicantId());
        final ProjectApplication projectApplication = loadProjectApplicantPort.loadBy(command.projectId(), command.applicantId());

        final AssignmentPeople projectConfirmation = assignPeople.confirm(project, people, projectApplication);
        projectApplicationConfirm(projectApplication);

        AssignmentPeopleId id = new AssignmentPeopleId(saveProjectConfirmationPort.save(projectConfirmation));
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
