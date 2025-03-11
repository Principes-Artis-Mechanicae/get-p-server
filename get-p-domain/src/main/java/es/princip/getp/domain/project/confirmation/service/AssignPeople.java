package es.princip.getp.domain.project.confirmation.service;

import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.confirmation.exception.NotCompletedProjectMeetingException;
import es.princip.getp.domain.project.confirmation.model.AssignmentPeople;
import org.springframework.stereotype.Component;


@Component
public class AssignPeople {

    public AssignmentPeople confirm(
        final Project project,
        final People people,
        final ProjectApplication projectApplication
    ) {
        if (!projectApplication.isMeetingCompleted()) {
            throw new NotCompletedProjectMeetingException();
        }

        return AssignmentPeople.builder()
            .applicantId(people.getId())
            .projectId(project.getId())
            .build();
    }
}
