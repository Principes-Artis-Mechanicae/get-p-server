package es.princip.getp.application.project.meeting;

import es.princip.getp.application.client.port.out.LoadClientPort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.apply.port.out.CheckProjectApplicationPort;
import es.princip.getp.application.project.apply.port.out.LoadProjectApplicantPort;
import es.princip.getp.application.project.apply.port.out.UpdateProjectApplicantPort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.application.project.meeting.dto.command.ScheduleMeetingCommand;
import es.princip.getp.application.project.meeting.exception.NotApplicantException;
import es.princip.getp.application.project.meeting.exception.NotClientOfProjectException;
import es.princip.getp.application.project.meeting.port.out.SaveProjectMeetingPort;
import es.princip.getp.domain.client.model.Client;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import es.princip.getp.domain.project.meeting.model.ProjectMeeting;
import es.princip.getp.domain.project.meeting.service.ProjectMeetingScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectMeetingService {

    private final LoadClientPort loadClientPort;
    private final LoadPeoplePort loadPeoplePort;
    private final LoadProjectApplicantPort loadProjectApplicantPort;
    private final LoadProjectPort loadProjectPort;
    private final UpdateProjectApplicantPort updateProjectApplicantPort;

    private final CheckProjectApplicationPort checkProjectApplicationPort;
    private final SaveProjectMeetingPort saveProjectMeetingPort;

    private final ProjectMeetingScheduler projectMeetingScheduler;

    private final MeetingSender meetingSender;

    /**
     * 미팅 신청
     * 
     * @param command 미팅 신청 명령
     * @return 미팅 ID
     */
    @Transactional
    public Long scheduleMeeting(final ScheduleMeetingCommand command) {
        final People people = loadPeoplePort.loadBy(command.applicantId());
        final ProjectApplication projectApplication = loadProjectApplicantPort.loadBy(command.projectId(), command.applicantId());
        final Client client = loadClientPort.loadBy(command.memberId());
        final Project project = loadProjectPort.loadBy(projectApplication.getProjectId());

        checkClientOfProject(client, project);
        checkPeopleIsApplicant(command.applicantId(), projectApplication.getProjectId());

        final ProjectMeeting projectMeeting = projectMeetingScheduler.schedule(
            project,
            people,
            projectApplication,
            command.location(),
            command.schedule(),
            command.phoneNumber(),
            command.description()
        );

        projectApplicationMeetingComplete(projectApplication);
        final Long meetingId = saveProjectMeetingPort.save(projectMeeting);

        meetingSender.send(people, project, projectMeeting);
        
        return meetingId;
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

    private void projectApplicationMeetingComplete(ProjectApplication projectApplication) {
        projectApplication.meetingComplete();
        updateProjectApplicantPort.update(projectApplication);
    }
}
