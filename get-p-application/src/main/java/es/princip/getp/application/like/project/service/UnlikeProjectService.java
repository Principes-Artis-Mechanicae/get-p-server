package es.princip.getp.application.like.project.service;

import es.princip.getp.application.like.exception.NeverLikedException;
import es.princip.getp.application.like.project.port.in.UnlikeProjectUseCase;
import es.princip.getp.application.like.project.port.out.CheckProjectLikePort;
import es.princip.getp.application.like.project.port.out.DeleteProjectLikePort;
import es.princip.getp.application.like.project.port.out.LoadProjectLikePort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.like.project.model.ProjectLike;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.project.commission.model.Project;
import es.princip.getp.domain.project.commission.model.ProjectId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UnlikeProjectService implements UnlikeProjectUseCase {

    private final LoadProjectLikePort loadProjectLikePort;
    private final LoadProjectPort loadProjectPort;
    private final CheckProjectLikePort checkProjectLikePort;
    private final DeleteProjectLikePort deleteProjectLikePort;

    @Transactional
    public void unlike(final MemberId memberId, final ProjectId projectId) {
        // TODO: 조회 성능 개선 필요
        final Project project = loadProjectPort.loadBy(projectId);
        checkNeverLiked(memberId, projectId);
        final ProjectLike projectLike = loadProjectLikePort.loadBy(memberId, projectId);
        deleteProjectLikePort.delete(projectLike);
    }

    private void checkNeverLiked(final MemberId memberId, final ProjectId projectId) {
        if (!checkProjectLikePort.existsBy(memberId, projectId)) {
            throw new NeverLikedException();
        }
    }
}