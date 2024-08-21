package es.princip.getp.application.projectMeeting.port.out;

import es.princip.getp.domain.project.command.domain.ProjectMeeting;

public interface SaveProjectMeetingPort {
    Long save(ProjectMeeting projectMeeting);
}
