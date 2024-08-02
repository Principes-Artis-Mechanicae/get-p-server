package es.princip.getp.domain.project.command.domain;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.project.exception.ProjectNeverLikedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectUnliker {

    private final ProjectLikeRepository projectLikeRepository;

    private void checkProjectNeverLiked(final Long peopleId, final Long projectId) {
        if (!projectLikeRepository.existsByPeopleIdAndProjectId(peopleId, projectId)) {
            throw new ProjectNeverLikedException();
        }
    }

    /**
     * 프로젝트에 눌렀던 좋아요를 취소한다.
     *
     * @param people 좋아요를 취소하는 피플
     * @param project 좋아요를 취소할 프로젝트
     * @throws ProjectNeverLikedException 프로젝트에 좋아요를 누른 적이 없는 경우
     */
    public void unlike(final People people, final Project project) {
        final Long peopleId = people.getPeopleId();
        final Long projectId = project.getProjectId();

        checkProjectNeverLiked(peopleId, projectId);

        projectLikeRepository.deleteByPeopleIdAndProjectId(peopleId, projectId);
    }
}
