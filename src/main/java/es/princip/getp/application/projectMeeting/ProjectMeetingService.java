package es.princip.getp.application.projectMeeting;

import es.princip.getp.application.projectMeeting.command.ScheduleMeetingCommand;
import es.princip.getp.application.projectMeeting.port.out.CheckProjectMeetingPort;
import es.princip.getp.application.projectMeeting.port.out.SaveProjectMeetingPort;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.exception.NotFoundPeopleException;
import es.princip.getp.domain.project.command.domain.*;
import es.princip.getp.domain.project.exception.NotApplicantException;
import es.princip.getp.domain.project.exception.NotClientOfProjectException;
import es.princip.getp.domain.project.exception.NotFoundProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectMeetingService {

    private final PeopleRepository peopleRepository;
    private final ProjectRepository projectRepository;

    private final ProjectApplicationRepository applicationRepository;

    private final SaveProjectMeetingPort saveProjectMeetingPort;
    private final CheckProjectMeetingPort checkProjectMeetingPort;

    private final MeetingSender meetingSender;

    /**
     * 미팅 신청
     * 
     * @param command 미팅 신청 명령
     * @return 미팅 ID
     */
    @Transactional
    public Long scheduleMeeting(final ScheduleMeetingCommand command) {
        final People people = peopleRepository.findByMemberId(command.applicantId())
            .orElseThrow(NotFoundPeopleException::new);
        final Project project = projectRepository.findById(command.projectId())
            .orElseThrow(NotFoundProjectException::new);

        checkMemberIsClientOfProject(command.memberId(), command.projectId());
        checkPeopleIsApplicant(command.applicantId(), command.projectId());

        final ProjectMeeting projectMeeting = ProjectMeeting.builder()
            .projectId(project.getProjectId())
            .applicantId(people.getPeopleId())
            .location(command.location())
            .schedule(command.schedule())
            .phoneNumber(command.phoneNumber())
            .description(command.description())
            .build();

        Long meetingId = saveProjectMeetingPort.save(projectMeeting);
        meetingSender.send(people, project, projectMeeting);
        
        return meetingId;
    }

    private void checkMemberIsClientOfProject(final Long memberId, final Long projectId) {
        if (!checkProjectMeetingPort.existsByProjectIdAndMemberId(projectId, memberId)) {
            throw new NotClientOfProjectException();
        }
    }

    private void checkPeopleIsApplicant(final Long applicantId, final Long projectId) {
        if (!applicationRepository.existsByApplicantIdAndProjectId(applicantId, projectId)) {
            throw new NotApplicantException();
        }
    }
}
