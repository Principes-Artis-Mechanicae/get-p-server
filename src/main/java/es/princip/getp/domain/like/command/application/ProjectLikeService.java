package es.princip.getp.domain.like.command.application;

import es.princip.getp.domain.like.command.domain.project.ProjectLiker;
import es.princip.getp.domain.like.command.domain.project.ProjectUnliker;
import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.people.exception.NotFoundPeopleException;
import es.princip.getp.domain.project.command.domain.Project;
import es.princip.getp.domain.project.command.domain.ProjectRepository;
import es.princip.getp.domain.project.exception.NotFoundProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectLikeService {

    private final ProjectRepository projectRepository;
    private final PeopleRepository peopleRepository;

    private final ProjectLiker projectLiker;
    private final ProjectUnliker projectUnliker;

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
        final Project project = projectRepository.findById(projectId)
            .orElseThrow(NotFoundProjectException::new);

        projectLiker.like(people, project);
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
        final Project project = projectRepository.findById(projectId)
            .orElseThrow(NotFoundProjectException::new);

        projectUnliker.unlike(people, project);
    }
}
