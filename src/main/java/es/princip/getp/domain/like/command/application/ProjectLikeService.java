package es.princip.getp.domain.like.command.application;

import es.princip.getp.application.people.port.out.LoadPeoplePort;
import es.princip.getp.application.project.commission.port.out.LoadProjectPort;
import es.princip.getp.domain.like.command.domain.project.ProjectLiker;
import es.princip.getp.domain.like.command.domain.project.ProjectUnliker;
import es.princip.getp.domain.people.model.People;
import es.princip.getp.domain.project.commission.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectLikeService {

    private final LoadProjectPort loadProjectPort;
    private final LoadPeoplePort loadPeoplePort;

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
        final People people = loadPeoplePort.loadBy(memberId);
        final Project project = loadProjectPort.loadBy(projectId);

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
        final People people = loadPeoplePort.loadBy(memberId);
        final Project project = loadProjectPort.loadBy(projectId);

        projectUnliker.unlike(people, project);
    }
}
