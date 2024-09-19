package es.princip.getp.application.like.project.service;

import es.princip.getp.application.like.project.port.out.CheckProjectLikePort;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.ProjectId;

import static org.mockito.BDDMockito.given;

public class LikeProjectMockingUtil {

    static void mockMemberNeverLikedProject(
        final CheckProjectLikePort checkProjectLikePort,
        final MemberId memberId,
        final ProjectId projectId
    ) {
        given(checkProjectLikePort.existsBy(memberId, projectId))
            .willReturn(false);
    }

    static void mockMemberAlreadyLikedProject(
         final CheckProjectLikePort checkProjectLikePort,
         final MemberId memberId,
         final ProjectId projectId
    ) {
        given(checkProjectLikePort.existsBy(memberId, projectId))
            .willReturn(true);
    }
}
