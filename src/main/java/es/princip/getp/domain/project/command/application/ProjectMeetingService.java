package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.exception.NotFoundPeopleException;
import es.princip.getp.domain.project.command.application.command.ScheduleMeetingCommand;
import es.princip.getp.domain.project.command.domain.*;
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
    private final ProjectMeetingRepository meetingRepository;

    private final ProjectMeetingScheduler meetingScheduler;
    private final MeetingSender meetingSender;

    /**
     * 미팅 신청
     * 
     * @param command 미팅 신청 명령
     * @return 미팅 ID
     */
    @Transactional
    public Long ScheduleMeeting(final ScheduleMeetingCommand command) {
        final People people = peopleRepository.findByMemberId(command.applicantId())
            .orElseThrow(NotFoundPeopleException::new);
        final Project project = projectRepository.findById(command.projectId())
            .orElseThrow(NotFoundProjectException::new);

        final ProjectMeeting projectMeeting = meetingScheduler.scheduleMeeting(
            command.memberId(),
            project,
            people,
            command.location(),
            command.schedule(),
            command.phoneNumber(),
            command.description()
        );

        meetingRepository.save(projectMeeting);
        meetingSender.send(people, project, projectMeeting);
        
        return projectMeeting.getMeetingId();
    }
}
