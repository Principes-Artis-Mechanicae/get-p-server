package es.princip.getp.application.like;

import es.princip.getp.application.like.exception.AlreadyLikedException;
import es.princip.getp.application.like.exception.NeverLikedException;
import es.princip.getp.application.like.port.out.project.CheckProjectLikePort;
import es.princip.getp.application.like.port.out.project.LoadProjectLikePort;
import es.princip.getp.application.like.port.out.project.ProjectLikePort;
import es.princip.getp.application.like.port.out.project.ProjectUnlikePort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.like.model.project.ProjectLike;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.exception.NotFoundPeopleException;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectLikeService {

    private final LoadProjectLikePort loadProjectLikePort;

    private final LoadProjectPort loadProjectPort;
    
    private final PeopleRepository peopleRepository;

    private final ProjectLikePort projectLikePort;

    private final ProjectUnlikePort projectUnlikePort;
    
    private final CheckProjectLikePort checkProjectLikePort;

    /**
     * 프로젝트 좋아요
     *
     * @param memberId 좋아요를 누를 피플의 사용자 ID
     * @param projectId 좋아요를 누를 프로젝트 ID
     */
    @Transactional
    public void like(final Long memberId, final Long projectId) {
        final People people = peopleRepository.findById(memberId)
            .orElseThrow(NotFoundPeopleException::new);
        final Project project = loadProjectPort.loadBy(projectId);

        checkAlreadyLiked(people.getPeopleId(), projectId);
        ProjectLike projectLike = buildProjectLike(people.getPeopleId(), projectId);
        
        projectLikePort.like(projectLike);
    }

    /**
     * 프로젝트 좋아요 취소
     *
     * @param memberId 좋아요를 취소할 피플의 사용자 ID
     * @param projectId 좋아요를 취소할 프로젝트 ID
     */
    @Transactional
    public void unlike(final Long memberId, final Long projectId) {
        final People people = peopleRepository.findById(memberId)
            .orElseThrow(NotFoundPeopleException::new);
        final Project project = loadProjectPort.loadBy(projectId);

        checkNeverLiked(people.getPeopleId(), projectId);
        ProjectLike projectLike = loadProjectLikePort.findByPeopleIdAndProjectId(people.getPeopleId(), projectId);

        projectUnlikePort.unlike(projectLike);
    }

    private void checkAlreadyLiked(final Long peopleId, final Long projectId) {
        if (checkProjectLikePort.existsByPeopleIdAndProjectId(peopleId, projectId)) {
            throw new AlreadyLikedException();
        }
    }

    private void checkNeverLiked(final Long peopleId, final Long projectId) {
        if (!checkProjectLikePort.existsByPeopleIdAndProjectId(peopleId, projectId)) {
            throw new NeverLikedException();
        }
    }

    private ProjectLike buildProjectLike(final Long peopleId, final Long projectId) {
        return ProjectLike.builder()
            .peopleId(peopleId)
            .projectId(projectId)
            .build();
    }
}