package es.princip.getp.application.like.project.service;

import es.princip.getp.application.like.exception.NeverLikedException;
import es.princip.getp.application.like.project.port.in.UnlikeProjectUseCase;
import es.princip.getp.application.like.project.port.out.CheckProjectLikePort;
import es.princip.getp.application.like.project.port.out.DeleteProjectLikePort;
import es.princip.getp.application.like.project.port.out.LoadProjectLikePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.like.model.project.ProjectLike;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UnlikeProjectService implements UnlikeProjectUseCase {

    private final LoadProjectLikePort loadProjectLikePort;
    private final LoadPeoplePort loadPeoplePort;
    private final LoadProjectPort loadProjectPort;
    private final CheckProjectLikePort checkProjectLikePort;
    private final DeleteProjectLikePort deleteProjectLikePort;

    @Transactional
    public void unlike(final Long memberId, final Long projectId) {
        final People people = loadPeoplePort.loadBy(memberId);
        // TODO: 조회 성능 개선 필요
        final Project project = loadProjectPort.loadBy(projectId);
        checkNeverLiked(people.getId(), projectId);
        final ProjectLike projectLike = loadProjectLikePort.loadBy(people.getId(), projectId);
        deleteProjectLikePort.delete(projectLike);
    }

    private void checkNeverLiked(final Long peopleId, final Long projectId) {
        if (!checkProjectLikePort.existsBy(peopleId, projectId)) {
            throw new NeverLikedException();
        }
    }
}