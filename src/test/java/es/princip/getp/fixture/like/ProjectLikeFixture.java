package es.princip.getp.fixture.like;

import es.princip.getp.domain.like.project.model.ProjectLike;
import es.princip.getp.domain.people.model.PeopleId;

public class ProjectLikeFixture {
    public static ProjectLike projectLike(PeopleId peopleId, Long projectId) {
        return ProjectLike.builder()
            .peopleId(peopleId)
            .projectId(projectId)
            .build();
    }
}
