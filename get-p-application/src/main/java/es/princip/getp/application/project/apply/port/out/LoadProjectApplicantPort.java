package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;
import es.princip.getp.domain.project.commission.model.ProjectId;

public interface LoadProjectApplicantPort {

    ProjectApplication loadBy(ProjectApplicationId applicationId);

    ProjectApplication loadBy(ProjectId projectId, PeopleId peopleId);
}
