package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.project.command.application.command.ScheduleMeetingCommand;
import es.princip.getp.domain.project.command.domain.*;
import es.princip.getp.infra.exception.BusinessLogicException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectMeetingService {

    private final PeopleRepository peopleRepository;
    
    private final ProjectRepository projectRepository;

    private final ProjectMeetingRepository projectMeetingRepository;

    private final ProjectMeetingApplier projectMeetingApplier;

    private final MeetingSender meetingSender;

    /**
     * 미팅 신청
     * 
     * @param command 미팅 신청 명령
     * @return 미팅 ID
     */
    @Transactional
    public Long ScheduleMeeting(final ScheduleMeetingCommand command) {
        
        // 프로젝트 지원한 피플이 존재하는지 확인
        final People people = peopleRepository.findByMemberId(command.applicantId())
            .orElseThrow(() -> new EntityNotFoundException("해당 피플이 존재하지 않습니다."));
        
        // 프로젝트가 존재하는지 확인
        final Project project = projectRepository.findById(command.projectId())
            .orElseThrow(() -> new EntityNotFoundException("해당 프로젝트가 존재하지 않습니다."));
        
        // 의뢰자 본인 프로젝트인지 검증
        boolean exist = projectMeetingRepository.existsByProjectIdAndMemberId(project.getProjectId(), command.memberId());
        if (!exist) {
            throw new BusinessLogicException("해당 프로젝트에 대한 권한이 없습니다.");
        }

        //TODO: 프로젝트에 지원한 피플이 맞는지 확인
        
        ProjectMeeting projectMeeting = projectMeetingApplier.scheduleMeeting(
            project,
            people,
            command.meetingLocation(),
            command.meetingSchedules(),
            command.contactPhoneNumber(),
            command.description()
        );

        meetingSender.send(people, project, projectMeeting);
        
        return projectMeetingRepository.save(projectMeeting).getMeetingId();
    }
}
