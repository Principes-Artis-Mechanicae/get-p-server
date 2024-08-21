package es.princip.getp.application.projectMeeting.port.out;

import es.princip.getp.domain.project.command.domain.ProjectMeeting;

public interface UpdateProjectMeetingPort {
    void updateProjectMeeting(ProjectMeeting projectMeeting);
}
