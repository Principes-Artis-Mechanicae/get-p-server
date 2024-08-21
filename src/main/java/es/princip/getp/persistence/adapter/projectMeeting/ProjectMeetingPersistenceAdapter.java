package es.princip.getp.persistence.adapter.projectMeeting;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import es.princip.getp.application.projectMeeting.port.out.CheckProjectMeetingPort;
import es.princip.getp.application.projectMeeting.port.out.LoadProjectMeetingPort;
import es.princip.getp.application.projectMeeting.port.out.SaveProjectMeetingPort;
import es.princip.getp.application.projectMeeting.port.out.UpdateProjectMeetingPort;
import es.princip.getp.domain.project.command.domain.ProjectMeeting;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
class ProjectMeetingPersistenceAdapter implements
            SaveProjectMeetingPort,
            LoadProjectMeetingPort,
            UpdateProjectMeetingPort,
            CheckProjectMeetingPort {
    
    private final ProjectMeetingRepository projectMeetingRepository;

    @Override
    public Long save(ProjectMeeting projectMeeting) {
        return projectMeetingRepository.save(projectMeeting).getMeetingId();
    }

    @Override
    public Optional<ProjectMeeting> loadProjectMeeting(Long meetingId) {
        return projectMeetingRepository.findById(meetingId);
    }

    @Override
    public void updateProjectMeeting(ProjectMeeting projectMeeting) {
        projectMeetingRepository.save(projectMeeting);
    }

    @Override
    public boolean existsByProjectIdAndMemberId(Long memberId, Long projectId) {
        return projectMeetingRepository.existsByProjectIdAndMemberId(projectId, memberId);
    }
}
