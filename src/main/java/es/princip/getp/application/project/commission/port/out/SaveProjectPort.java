package es.princip.getp.application.project.commission.port.out;

import es.princip.getp.domain.project.commission.model.Project;

public interface SaveProjectPort {

    Long save(Project project);
}
