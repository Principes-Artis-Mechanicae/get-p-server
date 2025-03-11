package es.princip.getp.application.project.commission.port.out;

import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;

public interface LoadProjectPort {

    Project loadBy(ProjectId projectId);
}
