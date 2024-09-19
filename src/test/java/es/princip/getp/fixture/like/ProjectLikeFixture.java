package es.princip.getp.fixture.like;

import es.princip.getp.domain.like.project.model.ProjectLike;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;

public class ProjectLikeFixture {
    public static ProjectLike projectLike(MemberId memberId, ProjectId projectId) {
        return ProjectLike.builder()
            .memberId(memberId)
            .projectId(projectId)
            .build();
    }
}
