package es.princip.getp.application.project.meeting.port.out;

import es.princip.getp.domain.project.meeting.model.ProjectMeeting;

public interface UpdateProjectMeetingPort {

    void update(ProjectMeeting projectMeeting);
}