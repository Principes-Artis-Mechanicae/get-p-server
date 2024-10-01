package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.domain.project.apply.model.ProjectApplication;
import es.princip.getp.domain.project.apply.model.ProjectApplicationId;

public interface LoadProjectApplicantPort {

    ProjectApplication loadBy(ProjectApplicationId applicationId);
}
