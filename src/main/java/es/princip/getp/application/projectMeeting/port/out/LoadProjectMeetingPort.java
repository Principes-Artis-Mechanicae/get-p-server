package es.princip.getp.application.projectMeeting.port.out;

import java.util.Optional;

import es.princip.getp.domain.project.command.domain.ProjectMeeting;

public interface LoadProjectMeetingPort {
    Optional<ProjectMeeting> loadProjectMeeting(Long meetingId);
}
