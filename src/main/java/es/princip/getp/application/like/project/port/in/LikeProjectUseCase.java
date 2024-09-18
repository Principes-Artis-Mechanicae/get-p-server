package es.princip.getp.application.like.project.port.in;

import es.princip.getp.domain.member.model.MemberId;

public interface LikeProjectUseCase {

    void like(MemberId memberId, Long projectId);
}
