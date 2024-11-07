package es.princip.getp.application.project.apply.port.out;

import es.princip.getp.domain.project.commission.model.ProjectId;

import java.util.Map;

public interface CountProjectApplicationPort {

    Map<ProjectId, Long> countBy(final ProjectId... projectId);
    Long countBy(final ProjectId projectId);
}
