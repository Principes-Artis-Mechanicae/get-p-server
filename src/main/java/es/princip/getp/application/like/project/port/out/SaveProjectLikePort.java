package es.princip.getp.application.like.project.port.out;

import es.princip.getp.domain.like.project.model.ProjectLike;

public interface SaveProjectLikePort {

    void save(ProjectLike like);
}
