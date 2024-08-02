package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.exception.AlreadyLikedProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectLiker {

    private final ProjectLikeRepository projectLikeRepository;

    private void checkProjectAlreadyLiked(final Long peopleId, final Long projectId) {
        if (projectLikeRepository.existsByPeopleIdAndProjectId(peopleId, projectId)) {
            throw new AlreadyLikedProjectException();
        }
    }

    /**
     * 프로젝트에 좋아요를 누른다.
     *
     * @param people 좋아요를 누르는 피플
     * @param project 좋아요를 누를 프로젝트
     * @return 좋아요 이력
     * @throws AlreadyLikedProjectException 이미 해당 프로젝트에 좋아요를 눌렀던 경우
     */
    public ProjectLike like(final People people, final Project project) {
        final Long peopleId = people.getPeopleId();
        final Long projectId = project.getProjectId();

        checkProjectAlreadyLiked(peopleId, projectId);

        return ProjectLike.of(peopleId, projectId);
    }
}
