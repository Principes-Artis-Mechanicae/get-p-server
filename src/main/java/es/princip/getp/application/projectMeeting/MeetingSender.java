package es.princip.getp.application.projectMeeting;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectMeeting;

public interface MeetingSender {
    void send(People people, Project project, ProjectMeeting projectMeeting);
}
