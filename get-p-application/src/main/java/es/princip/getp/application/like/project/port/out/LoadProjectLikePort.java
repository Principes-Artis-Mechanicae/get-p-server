package es.princip.getp.application.like.project.port.out;

import es.princip.getp.domain.like.project.model.ProjectLike;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;

public interface LoadProjectLikePort {

    ProjectLike loadBy(MemberId memberId, ProjectId projectId);
}
