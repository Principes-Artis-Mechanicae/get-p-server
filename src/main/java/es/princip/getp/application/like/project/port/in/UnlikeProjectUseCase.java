package es.princip.getp.application.like.project.port.in;

import es.princip.getp.domain.member.model.MemberId;

public interface UnlikeProjectUseCase {

    void unlike(MemberId memberId, Long projectId);
}
