package es.princip.getp.application.like.project.port.in;

import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;

public interface UnlikeProjectUseCase {

    void unlike(MemberId memberId, ProjectId projectId);
}
