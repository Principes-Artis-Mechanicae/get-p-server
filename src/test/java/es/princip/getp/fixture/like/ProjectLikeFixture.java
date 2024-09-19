package es.princip.getp.fixture.like;

import es.princip.getp.domain.like.project.model.ProjectLike;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.ProjectId;

public class ProjectLikeFixture {
    public static ProjectLike projectLike(PeopleId peopleId, ProjectId projectId) {
        return ProjectLike.builder()
            .peopleId(peopleId)
            .projectId(projectId)
            .build();
    }
}
