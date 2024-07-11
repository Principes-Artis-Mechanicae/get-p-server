package es.princip.getp.domain.project.service;

import es.princip.getp.domain.people.domain.People;
import es.princip.getp.domain.people.service.PeopleService;
import es.princip.getp.domain.project.domain.Project;
import es.princip.getp.domain.project.domain.ProjectLike;
import es.princip.getp.domain.project.exception.ProjectLikeErrorCode;
import es.princip.getp.domain.project.repository.ProjectLikeRepository;
import es.princip.getp.infra.exception.BusinessLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectLikeService {

    private final ProjectService projectService;
    private final PeopleService peopleService;
    private final ProjectLikeRepository projectLikeRepository;

    private void checkProjectIsAlreadyLiked(Long peopleId, Long projectId) {
        if (projectLikeRepository.existsByPeople_PeopleIdAndProject_ProjectId(peopleId, projectId)) {
            throw new BusinessLogicException(ProjectLikeErrorCode.ALREADY_LIKED);
        }
    }

    @Transactional
    public void like(Long memberId, Long projectId) {
        People people = peopleService.getByMemberId(memberId);
        Project project = projectService.getByProjectId(projectId);
        checkProjectIsAlreadyLiked(people.getPeopleId(), projectId);
        projectLikeRepository.save(
            ProjectLike.builder()
                .people(people)
                .project(project)
                .build()
        );
    }
}
