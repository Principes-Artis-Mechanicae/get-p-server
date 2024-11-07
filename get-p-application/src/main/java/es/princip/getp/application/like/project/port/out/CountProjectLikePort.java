package es.princip.getp.application.like.project.port.out;

import es.princip.getp.domain.project.commission.model.ProjectId;

import java.util.Map;

public interface CountProjectLikePort {

    Long countBy(ProjectId projectId);

    Map<ProjectId, Long> countBy(ProjectId... projectIds);
}
