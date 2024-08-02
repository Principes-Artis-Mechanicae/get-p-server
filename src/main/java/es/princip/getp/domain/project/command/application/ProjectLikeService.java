package es.princip.getp.domain.project.command.application;

import es.princip.getp.domain.people.command.domain.People;
import es.princip.getp.domain.people.command.domain.PeopleRepository;
import es.princip.getp.domain.project.command.domain.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectLikeService {

    private final ProjectRepository projectRepository;
    private final PeopleRepository peopleRepository;
    private final ProjectLikeRepository projectLikeRepository;

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
            .orElseThrow(() -> new EntityNotFoundException("해당 피플을 찾을 수 없습니다."));
        final Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException("해당 프로젝트를 찾을 수 없습니다."));

        final ProjectLike like = projectLiker.like(people, project);

        projectLikeRepository.save(like);
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
            .orElseThrow(() -> new EntityNotFoundException("해당 피플을 찾을 수 없습니다."));
        final Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new EntityNotFoundException("해당 프로젝트를 찾을 수 없습니다."));

        projectUnliker.unlike(people, project);
    }
}
