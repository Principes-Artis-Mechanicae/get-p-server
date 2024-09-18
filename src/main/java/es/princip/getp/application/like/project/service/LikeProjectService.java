package es.princip.getp.application.like.project.service;

import es.princip.getp.application.like.exception.AlreadyLikedException;
import es.princip.getp.application.like.project.port.in.LikeProjectUseCase;
import es.princip.getp.application.like.project.port.out.CheckProjectLikePort;
import es.princip.getp.application.like.project.port.out.SaveProjectLikePort;
import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.like.project.model.ProjectLike;
import es.princip.getp.domain.member.model.MemberId;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.people.model.PeopleId;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class LikeProjectService implements LikeProjectUseCase {

    private final LoadProjectPort loadProjectPort;
    private final LoadPeoplePort loadPeoplePort;
    private final CheckProjectLikePort checkProjectLikePort;
    private final SaveProjectLikePort saveProjectLikePort;

    /**
     * 프로젝트 좋아요
     *
     * @param memberId 좋아요를 누를 피플의 사용자 ID
     * @param projectId 좋아요를 누를 프로젝트 ID
     */
    @Transactional
    public void like(final MemberId memberId, final Long projectId) {
        final People people = loadPeoplePort.loadBy(memberId);
        // TODO: 조회 성능 개선 필요
        final Project project = loadProjectPort.loadBy(projectId);
        checkAlreadyLiked(people.getId(), projectId);
        final ProjectLike projectLike = buildProjectLike(people.getId(), projectId);
        saveProjectLikePort.save(projectLike);
    }

    private void checkAlreadyLiked(final PeopleId peopleId, final Long projectId) {
        if (checkProjectLikePort.existsBy(peopleId, projectId)) {
            throw new AlreadyLikedException();
        }
    }

    private ProjectLike buildProjectLike(final PeopleId peopleId, final Long projectId) {
        return ProjectLike.builder()
            .peopleId(peopleId)
            .projectId(projectId)
            .build();
    }
}