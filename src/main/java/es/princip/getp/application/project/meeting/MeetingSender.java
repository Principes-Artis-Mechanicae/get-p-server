package es.princip.getp.application.project.meeting;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.meeting.model.ProjectMeeting;

public interface MeetingSender {

    void send(People people, Project project, ProjectMeeting projectMeeting);
}
