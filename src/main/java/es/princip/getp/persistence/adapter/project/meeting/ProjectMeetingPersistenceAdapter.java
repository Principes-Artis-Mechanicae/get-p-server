package es.princip.getp.persistence.adapter.project.meeting;

import es.princip.getp.application.project.meeting.port.out.CheckProjectMeetingPort;
import es.princip.getp.application.project.meeting.port.out.LoadProjectMeetingPort;
import es.princip.getp.application.project.meeting.port.out.SaveProjectMeetingPort;
import es.princip.getp.application.project.meeting.port.out.UpdateProjectMeetingPort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.meeting.model.ProjectMeeting;
import es.princip.getp.persistence.adapter.project.meeting.model.ProjectMeetingJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ProjectMeetingPersistenceAdapter implements
        SaveProjectMeetingPort,
        LoadProjectMeetingPort,
        UpdateProjectMeetingPort,
        CheckProjectMeetingPort {

    private final ProjectMeetingPersistenceMapper mapper;
    private final ProjectMeetingRepository repository;

    @Override
    public Long save(final ProjectMeeting projectMeeting) {
        final ProjectMeetingJpaEntity jpaEntity = mapper.mapToJpa(projectMeeting);
        return repository.save(jpaEntity).getMeetingId();
    }

    @Override
    public ProjectMeeting loadBy(final Long meetingId) {
        final ProjectMeetingJpaEntity jpaEntity = repository.findById(meetingId)
            .orElseThrow(NotFoundProjectMeetingException::new);
        return mapper.mapToDomain(jpaEntity);
    }

    @Override
    public void update(final ProjectMeeting projectMeeting) {
        final Long meetingId = projectMeeting.getMeetingId();
        if (!repository.existsById(meetingId)) {
            throw new NotFoundProjectMeetingException();
        }
        final ProjectMeetingJpaEntity jpaEntity = mapper.mapToJpa(projectMeeting);
        repository.save(jpaEntity);
    }

    @Override
    public boolean existsApplicantBy(final MemberId memberId, final Long projectId) {
        return repository.existsApplicantBy(projectId, memberId);
    }
}
