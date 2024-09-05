package es.princip.getp.application.like.project.port.out;

import es.princip.getp.domain.like.model.project.ProjectLike;

public interface LoadProjectLikePort {

    ProjectLike loadBy(Long peopleId, Long projectId);
}
