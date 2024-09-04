package es.princip.getp.application.like.port.out.project;

import es.princip.getp.domain.like.model.project.ProjectLike;

public interface ProjectUnlikePort {
    void unlike(ProjectLike projectLike);
}
