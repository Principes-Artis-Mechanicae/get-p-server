package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.domain.project.apply.model.ProjectApplication;

public interface SaveProjectApplicationPort {

    Long save(ProjectApplication projectApplication);
}
