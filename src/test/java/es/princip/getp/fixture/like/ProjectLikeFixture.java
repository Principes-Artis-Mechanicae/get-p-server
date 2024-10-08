package es.princip.getp.fixture.like;

import es.princip.getp.domain.like.project.model.ProjectLike;

public class ProjectLikeFixture {
    public static ProjectLike projectLike(Long peopleId, Long projectId) {
        return ProjectLike.builder()
            .peopleId(peopleId)
            .projectId(projectId)
            .build();
    }
}
